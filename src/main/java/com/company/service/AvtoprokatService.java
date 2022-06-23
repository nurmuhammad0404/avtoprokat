package com.company.service;

import com.company.dto.AvtoprokatDTO;
import com.company.entity.*;
import com.company.enums.CarStatus;
import com.company.enums.DistrStatus;
import com.company.enums.DriverStatus;
import com.company.exc.*;
import com.company.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class AvtoprokatService {
    @Autowired
    private AvtoprokatRepository avtoprokatRepository;
    @Autowired
    private DistrService distrService;
    @Autowired
    private CarService carService;
    @Autowired
    private DriverService driverService;
    @Autowired
    private DistrRepository distrRepository;
    @Autowired
    private CarRepository carRepository;
    @Autowired
    private DriverRepository driverRepository;

    public AvtoprokatDTO create(AvtoprokatDTO dto) {
//        if (dto.getDistrCode() == null) {
//            throw new AppBadRequestException("Distr code kritilmadi");
//        }
//        if (dto.getDistrName() == null){
//            throw new AppBadRequestException("Distr ismi kiritlmadi");
//        }
//        if (dto.getDistrUserName() == null){
//            throw new AppBadRequestException("Distr username kiritilmadi");
//        }
//        if (dto.getDriverName() == null){
//            throw new AppBadRequestException("Haydovchi ismi kiritilmadi");
//        }
//        if (dto.getCarNumber() == null){
//            throw new AppBadRequestException("Mashina raqami kiritilmadi");
//        }
//        if (dto.getTeritory() == null){
//            throw new AppBadRequestException("Hudud kirilmadi");
//        }
//
//        //distr
//        Optional<DistrEntity> distr = distrRepository.findByDistrCode(dto.getDistrCode());
//        if (distr.isEmpty() || distr.get().getVisible().equals(false)) {
//            throw new ItemNotFoundException("Bunday codli distr mavjud emas");
//        }
//
//        if (distr.get().getStatus().equals(DistrStatus.ACTIVE)) {
//            throw new ItemActiveException("Distr active");
//        }
//        Optional<DistrEntity> distrUsername = distrRepository.findByUserName(dto.getDistrUserName());
//        if (distrUsername.isEmpty()) {
//            throw new ItemNotFoundException("Bunday usernameli distr topilmadi");
//        }
//
//        //distr parametrlari mosligi tekshiriladi
//        Optional<DistrEntity> distrCodeName = distrRepository.findByDistrCodeAndName(dto.getDistrCode(), dto.getDistrName());
//        if (distrCodeName.isEmpty()){
//            throw new ItemNotFoundException("Distr code va ismi mos kelmadi");
//        }
//        Optional<DistrEntity> nameUserName = distrRepository.findByNameAndUserName(dto.getDistrName(), dto.getDistrUserName());
//        if (nameUserName.isEmpty()){
//            throw new ItemNotFoundException("Distr ismi va username mos kelmadi");
//        }
//        Optional<DistrEntity> codeUserName = distrRepository.findByDistrCodeAndUserName(dto.getDistrCode(), dto.getDistrUserName());
//        if (codeUserName.isEmpty()){
//            throw new ItemNotFoundException("Distr code va username mos kelmadi");
//        }
//
//        //car
//        Optional<CarEntity> car = carRepository.findByNumber(dto.getCarNumber());
//        if (car.get().getVisible().equals(false)) {
//            throw new ItemNotFoundException("Bunday raqamli mashina mavjud emas");
//        }
//        if (car.get().getStatus().equals(CarStatus.ACTIVE)) {
//            throw new ItemActiveException("Mashina active");
//        }
//
//        //driver
//        Optional<DriverEnitity> driver = driverRepository.findByUserName(dto.getDriverUserName());
//        if (driver.get().getVisible().equals(false)) {
//            throw new ItemNotFoundException("Bunday usernameli haydovchi mavjud emas");
//        }
//        Optional<DriverEnitity> name = driverRepository.findByName(dto.getDriverName());
//        if (name.isEmpty()){
//            throw new ItemNotFoundException("Bunday ismli haydovchi mavjud emas");
//        }
//        if (driver.get().getStatus().equals(DriverStatus.ACTIVE)) {
//            throw new ItemActiveException("Haydovchi active");
//        }
//
//        //driver parametrlari tekshiriladi
//        Optional<DriverEnitity> driverNameUserName = driverRepository.findByNameAndUserName(dto.getDriverName(), dto.getDriverUserName());
//        if (driverNameUserName.isEmpty()){
//            throw new ItemNotFoundException("Haydovchi ismi va userName mos kelmadi");
//        }
        avtoprokatValidation(dto);

    /*    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("d/M/yyyy");
        LocalDate localDate1;
        try {
            localDate1 = LocalDate.parse(dto.getWorkDate(), dateTimeFormatter);
        } catch (RuntimeException e) {
            throw new DateFormatWrongException("Sana " + LocalDate.now().getDayOfMonth() + "/" + LocalDate.now().getMonthValue()
                    + "/" + LocalDate.now().getYear() + " ko'rinishida berilishi kerak");
        }*/

        AvtoprokatEntity entity = new AvtoprokatEntity();
        entity.setDistrCode(dto.getDistrCode());

        if (dto.getWorkDate() == null){
            entity.setWorkDate(LocalDate.now());
            String localDate = LocalDate.now().getDayOfMonth() + "/" + LocalDate.now().
                    getMonthValue() +
                    "/" + LocalDate.now().getYear();
            dto.setWorkDate(localDate);
        }else {
            LocalDate localDate = checkWorkDateFormat(dto.getWorkDate());
            entity.setWorkDate(localDate);
        }

        entity.setCarNumber(dto.getCarNumber());
        entity.setDistrName(dto.getDistrName());
        entity.setDriverName(dto.getDriverName());
        entity.setTeritory(dto.getTeritory());
        entity.setDistrUserName(dto.getDistrUserName());
        entity.setDriverName(dto.getDriverName());
        entity.setDriverUserName(dto.getDriverUserName());
        entity.setCreatedDate(LocalDateTime.now());

        avtoprokatRepository.save(entity);

        distrService.changeStatus(entity.getDistrCode(), DistrStatus.ACTIVE);
        carService.changeStatus(entity.getCarNumber(), CarStatus.ACTIVE );
        driverService.changeStatus(entity.getDriverUserName(), DriverStatus.ACTIVE);

        dto.setId(entity.getId());
        dto.setCreatedDate(entity.getCreatedDate());

        return dto;
    }

    public List<AvtoprokatDTO> getList(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdDate");

        Page<AvtoprokatEntity> avtoprokatEntityList = avtoprokatRepository.findAll(pageable);

        List<AvtoprokatDTO> avtoprokatDTOList = new LinkedList<>();

        avtoprokatEntityList.forEach(avtoprokatEntity -> avtoprokatDTOList.add(toDTO(avtoprokatEntity)));

        return avtoprokatDTOList;
    }

//    public List<AvtoprokatDTO> getListByWorkDate(String workDate, int size, int page) {
//        Pageable pageable = PageRequest.of(size, page);
//        LocalDate localDate = checkWorkDateFormat(workDate);
//        Page<AvtoprokatEntity> avtoprokatEntities = avtoprokatRepository.findAllByCreatedDate(localDate, pageable);
//
//        List<AvtoprokatDTO> avtoprokatDTOList = new LinkedList<>();
//
//        avtoprokatEntities.forEach(avtoprokatEntity -> avtoprokatDTOList.add(toDTO(avtoprokatEntity)));
//
//        return avtoprokatDTOList;
//    }

    public static LocalDate checkWorkDateFormat(String workDate){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("d/M/yyyy");
        LocalDate localDate1;
        try {
            localDate1 = LocalDate.parse(workDate, dateTimeFormatter);
        }catch (RuntimeException e){
            throw new DateFormatWrongException("Sana " + LocalDate.now().getDayOfMonth() + "/" + LocalDate.now().getMonthValue()
                    + "/" + LocalDate.now().getYear() + " ko'rinishida berilishi kerak");
        }

        return localDate1;

    }

    public AvtoprokatDTO toDTO(AvtoprokatEntity entity) {
        AvtoprokatDTO dto = new AvtoprokatDTO();
        dto.setId(entity.getId());
        dto.setDistrCode(entity.getDistrCode());
        dto.setDriverName(entity.getDriverName());
        dto.setCarNumber(entity.getCarNumber());
        dto.setTeritory(entity.getTeritory());
        dto.setDistrName(entity.getDistrName());
        dto.setDistrUserName(entity.getDistrUserName());
        dto.setDriverUserName(entity.getDriverUserName());
        dto.setWorkDate(String.valueOf(entity.getWorkDate().getDayOfMonth()) + entity.getWorkDate().getMonthValue()
                + entity.getWorkDate().getYear());
        dto.setCreatedDate(entity.getCreatedDate());

        return dto;
    }

    public void avtoprokatValidation(AvtoprokatDTO dto){

        if (dto.getDistrCode() == null) {
            throw new AppBadRequestException("Distr code kritilmadi");
        }
        if (dto.getDistrName() == null){
            throw new AppBadRequestException("Distr ismi kiritlmadi");
        }
        if (dto.getDistrUserName() == null){
            throw new AppBadRequestException("Distr username kiritilmadi");
        }
        if (dto.getDriverName() == null){
            throw new AppBadRequestException("Haydovchi ismi kiritilmadi");
        }
        if (dto.getCarNumber() == null){
            throw new AppBadRequestException("Mashina raqami kiritilmadi");
        }
        if (dto.getTeritory() == null){
            throw new AppBadRequestException("Hudud kirilmadi");
        }

        //distr
        Optional<DistrEntity> distr = distrRepository.findByDistrCode(dto.getDistrCode());
        if (distr.isEmpty() || distr.get().getVisible().equals(false)) {
            throw new ItemNotFoundException("Bunday codli distr mavjud emas");
        }

        if (distr.get().getStatus().equals(DistrStatus.ACTIVE)) {
            throw new ItemActiveException("Distr active");
        }
        Optional<DistrEntity> distrUsername = distrRepository.findByUserName(dto.getDistrUserName());
        if (distrUsername.isEmpty()) {
            throw new ItemNotFoundException("Bunday usernameli distr topilmadi");
        }

        //distr parametrlari mosligi tekshiriladi
        Optional<DistrEntity> distrCodeName = distrRepository.findByDistrCodeAndName(dto.getDistrCode(), dto.getDistrName());
        if (distrCodeName.isEmpty()){
            throw new ItemNotFoundException("Distr code va ismi mos kelmadi");
        }
        Optional<DistrEntity> nameUserName = distrRepository.findByNameAndUserName(dto.getDistrName(), dto.getDistrUserName());
        if (nameUserName.isEmpty()){
            throw new ItemNotFoundException("Distr ismi va username mos kelmadi");
        }
        Optional<DistrEntity> codeUserName = distrRepository.findByDistrCodeAndUserName(dto.getDistrCode(), dto.getDistrUserName());
        if (codeUserName.isEmpty()){
            throw new ItemNotFoundException("Distr code va username mos kelmadi");
        }

        //car
        Optional<CarEntity> car = carRepository.findByNumber(dto.getCarNumber());
        if (car.get().getVisible().equals(false)) {
            throw new ItemNotFoundException("Bunday raqamli mashina mavjud emas");
        }
        if (car.get().getStatus().equals(CarStatus.ACTIVE)) {
            throw new ItemActiveException("Mashina active");
        }

        //driver
        Optional<DriverEnitity> driver = driverRepository.findByUserName(dto.getDriverUserName());
        if (driver.get().getVisible().equals(false)) {
            throw new ItemNotFoundException("Bunday usernameli haydovchi mavjud emas");
        }
        Optional<DriverEnitity> name = driverRepository.findByName(dto.getDriverName());
        if (name.isEmpty()){
            throw new ItemNotFoundException("Bunday ismli haydovchi mavjud emas");
        }
        if (driver.get().getStatus().equals(DriverStatus.ACTIVE)) {
            throw new ItemActiveException("Haydovchi active");
        }

        //driver parametrlari tekshiriladi
        Optional<DriverEnitity> driverNameUserName = driverRepository.findByNameAndUserName(dto.getDriverName(), dto.getDriverUserName());
        if (driverNameUserName.isEmpty()){
            throw new ItemNotFoundException("Haydovchi ismi va userName mos kelmadi");
        }
        List<AvtoprokatEntity> teritory = avtoprokatRepository.findByCreatedDateBetween(LocalDateTime.now().minusMinutes(10), LocalDateTime.now().minusMinutes(1));
        if (!teritory.isEmpty()){

        }

    }

    @Scheduled(fixedRate = 300000)
    public void checkStatus(){
        List<AvtoprokatEntity> avtoprokatEntityList = avtoprokatRepository.findByCreatedDateBetween(LocalDateTime.now().minusMinutes(10), LocalDateTime.now().minusMinutes(1));
//        for (AvtoprokatEntity entity : avtoprokatEntityList){
//            System.out.println(entity + " || " + "hours: " + entity.getCreatedDate().getHour() + " minutes: " + entity.getCreatedDate().getMinute() +
//                    " day: " + entity.getCreatedDate().getDayOfMonth() + " month: " + entity.getCreatedDate().getMonthValue());
//        }
        for (AvtoprokatEntity entity: avtoprokatEntityList){
            distrRepository.changeStatus(DistrStatus.NOTACTIVE, entity.getDistrCode());
            driverRepository.changeStatus(DriverStatus.NOTACTIVE, entity.getDriverUserName());
            carRepository.changeStatus(CarStatus.NOTACTIVE, entity.getCarNumber());
        }

    }

}
