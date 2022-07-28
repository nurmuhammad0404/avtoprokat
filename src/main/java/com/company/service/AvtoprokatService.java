package com.company.service;

import com.company.dto.AvtoprokatDTO;
import com.company.entity.*;
import com.company.enums.CarStatus;
import com.company.enums.DistrStatus;
import com.company.enums.DriverStatus;
import com.company.enums.TeritoryStatus;
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
import java.util.*;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

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

    @Autowired
    private TeritoryRepository teritoryRepository;
    @Autowired
    private TeritoryService teritoryService;

    public AvtoprokatDTO create(AvtoprokatDTO dto) {

        avtoprokatValidation(dto);

        AvtoprokatEntity entity = new AvtoprokatEntity();
        entity.setDistrCode(dto.getDistrCode());

        if (dto.getWorkDate() == null) {
            entity.setWorkDate(LocalDate.now());
            String localDate = LocalDate.now().getDayOfMonth() + "/" + LocalDate.now().
                    getMonthValue() +
                    "/" + LocalDate.now().getYear();
            dto.setWorkDate(localDate);
        } else {
            LocalDate localDate = checkWorkDateFormat(dto.getWorkDate());
            entity.setWorkDate(localDate);
        }

//        ProfileEntity profile = profileService.getById(pId);

        entity.setCarNumber(dto.getCarNumber());
        entity.setDistrName(dto.getDistrName());
        entity.setDriverName(dto.getDriverName());
        entity.setTeritory(dto.getTeritory());
        entity.setDistrUserName(dto.getDistrUserName());
        entity.setDriverName(dto.getDriverName());
        entity.setDriverUserName(dto.getDriverUserName());
//        entity.setProfileId(pId);
//        entity.setProfile(profile);
        entity.setCreatedDate(LocalDateTime.now());

        avtoprokatRepository.save(entity);

        distrService.changeStatus(entity.getDistrCode(), DistrStatus.ACTIVE);
        carService.changeStatus(entity.getCarNumber(), CarStatus.ACTIVE);
        driverService.changeStatus(entity.getDriverUserName(), DriverStatus.ACTIVE);
        teritoryService.changeStatus(entity.getTeritory(), TeritoryStatus.ACTIVE);

        dto.setId(entity.getId());
        dto.setProfileId(entity.getProfileId());
        dto.setProfile(entity.getProfile());
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

    public Boolean changeStatus(Integer id) {
        AvtoprokatEntity entity = avtoprokatRepository.findById(id).orElseThrow(() -> new ItemNotFoundException("Avoprokat topilmadi"));

        distrService.changeStatus(entity.getDistrCode(), DistrStatus.NOTACTIVE);
        carService.changeStatus(entity.getCarNumber(), CarStatus.NOTACTIVE);
        driverService.changeStatus(entity.getDriverUserName(), DriverStatus.NOTACTIVE);
        teritoryService.changeStatus(entity.getTeritory(), TeritoryStatus.NOTACTIVE);

        return true;
    }

    public AvtoprokatDTO update(Integer id, AvtoprokatDTO dto) {
        AvtoprokatEntity entity = avtoprokatRepository.findById(id).orElseThrow(() -> new ItemNotFoundException("Avoprokat topilmadi"));

        avtoprokatValidation(dto);

        entity.setDistrCode(dto.getDistrCode());

        if (dto.getWorkDate() == null) {
            entity.setWorkDate(LocalDate.now());
            String localDate = LocalDate.now().getDayOfMonth() + "/" + LocalDate.now().
                    getMonthValue() +
                    "/" + LocalDate.now().getYear();
            dto.setWorkDate(localDate);
        } else {
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
        avtoprokatRepository.save(entity);

        distrService.changeStatus(entity.getDistrCode(), DistrStatus.ACTIVE);
        carService.changeStatus(entity.getCarNumber(), CarStatus.ACTIVE);
        driverService.changeStatus(entity.getDriverUserName(), DriverStatus.ACTIVE);
        teritoryService.changeStatus(entity.getTeritory(), TeritoryStatus.ACTIVE);

        toDTO(entity);

        return dto;

    }


    public static LocalDate checkWorkDateFormat(String workDate) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("d/M/yyyy");
        LocalDate localDate1;
        try {
            localDate1 = LocalDate.parse(workDate, dateTimeFormatter);
        } catch (RuntimeException e) {
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
        dto.setProfileId(entity.getProfileId());
//        dto.setProfile(entity.getProfile());
        dto.setCreatedDate(entity.getCreatedDate());

        return dto;
    }

    public void avtoprokatValidation(AvtoprokatDTO dto) {

        if (dto.getDistrCode() == null) {
            throw new AppBadRequestException("Distr code kritilmadi");
        }
        if (dto.getDistrName() == null) {
            throw new AppBadRequestException("Distr ismi kiritlmadi");
        }
        if (dto.getDistrUserName() == null) {
            throw new AppBadRequestException("Distr username kiritilmadi");
        }
        if (dto.getDriverName() == null) {
            throw new AppBadRequestException("Haydovchi ismi kiritilmadi");
        }
        if (dto.getCarNumber() == null) {
            throw new AppBadRequestException("Mashina raqami kiritilmadi");
        }
        if (dto.getTeritory() == null) {
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
        if (distrCodeName.isEmpty()) {
            throw new ItemNotFoundException("Distr code va ismi mos kelmadi");
        }
        Optional<DistrEntity> nameUserName = distrRepository.findByNameAndUserName(dto.getDistrName(), dto.getDistrUserName());
        if (nameUserName.isEmpty()) {
            throw new ItemNotFoundException("Distr ismi va username mos kelmadi");
        }
        Optional<DistrEntity> codeUserName = distrRepository.findByDistrCodeAndUserName(dto.getDistrCode(), dto.getDistrUserName());
        if (codeUserName.isEmpty()) {
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
        if (name.isEmpty()) {
            throw new ItemNotFoundException("Bunday ismli haydovchi mavjud emas");
        }
        if (driver.get().getStatus().equals(DriverStatus.ACTIVE)) {
            throw new ItemActiveException("Haydovchi active");
        }

        //driver parametrlari tekshiriladi
        Optional<DriverEnitity> driverNameUserName = driverRepository.findByNameAndUserName(dto.getDriverName(), dto.getDriverUserName());
        if (driverNameUserName.isEmpty()) {
            throw new ItemNotFoundException("Haydovchi ismi va userName mos kelmadi");
        }

        //teritory parametrllar tekshiriladi
        Optional<TeritoryEntity> teritory = teritoryRepository.findByName(dto.getTeritory());
        if (teritory.isEmpty()) {
            throw new ItemNotFoundException("Bunday nomli hudud mavjud emas");
        }

    }

//    @Scheduled(fixedRate = 300000)
//    @Scheduled(cron = "0 0/34 12-14 * * *")
    @Scheduled(cron = "0 0 20,22 * * *")
    public void checkStatus() {
        System.out.println(LocalDateTime.now());
        List<AvtoprokatEntity> avtoprokatEntityList = avtoprokatRepository.findByCreatedDateBetween(LocalDateTime.now().minusMinutes(60), LocalDateTime.now().minusMinutes(5));
//        for (AvtoprokatEntity entity : avtoprokatEntityList){
//            System.out.println(entity + " || " + "hours: " + entity.getCreatedDate().getHour() + " minutes: " + entity.getCreatedDate().getMinute() +
//                    " day: " + entity.getCreatedDate().getDayOfMonth() + " month: " + entity.getCreatedDate().getMonthValue());
//        }
        for (AvtoprokatEntity entity : avtoprokatEntityList) {
            distrRepository.changeStatus(DistrStatus.NOTACTIVE, entity.getDistrCode());
            driverRepository.changeStatus(DriverStatus.NOTACTIVE, entity.getDriverUserName());
            carRepository.changeStatus(CarStatus.NOTACTIVE, entity.getCarNumber());
            teritoryRepository.changeStatus(TeritoryStatus.NOTACTIVE, entity.getTeritory());
        }

    }

}
