package com.azad.jsonplaceholderclone.services.impl;

import com.azad.jsonplaceholderclone.models.Address;
import com.azad.jsonplaceholderclone.models.Company;
import com.azad.jsonplaceholderclone.models.Geo;
import com.azad.jsonplaceholderclone.models.MemberProfile;
import com.azad.jsonplaceholderclone.models.dtos.AddressDto;
import com.azad.jsonplaceholderclone.models.dtos.CompanyDto;
import com.azad.jsonplaceholderclone.models.dtos.MemberProfileDto;
import com.azad.jsonplaceholderclone.models.entities.AddressEntity;
import com.azad.jsonplaceholderclone.models.entities.CompanyEntity;
import com.azad.jsonplaceholderclone.models.entities.GeoEntity;
import com.azad.jsonplaceholderclone.models.entities.MemberProfileEntity;
import com.azad.jsonplaceholderclone.repos.*;
import com.azad.jsonplaceholderclone.security.auth.api.AuthService;
import com.azad.jsonplaceholderclone.security.entities.MemberEntity;
import com.azad.jsonplaceholderclone.services.MemberProfileService;
import com.azad.jsonplaceholderclone.utils.PagingAndSorting;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class MemberProfileServiceImpl implements MemberProfileService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AuthService authService;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private GeoRepository geoRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private MemberRepository memberRepository;

    private final MemberProfileRepository memberProfileRepository;

    @Autowired
    public MemberProfileServiceImpl(MemberProfileRepository memberProfileRepository) {
        this.memberProfileRepository = memberProfileRepository;
    }

    @Override
    public MemberProfileDto create(MemberProfileDto requestBody) {

        Address address = requestBody.getAddress();
        Company company = requestBody.getCompany();

        AddressEntity savedAddress = saveAddress(modelMapper.map(address, AddressDto.class));
        CompanyEntity savedCompany = saveCompany(modelMapper.map(company, CompanyDto.class));

        MemberEntity memberEntity = authService.getLoggedInMember();

        MemberProfileEntity memberProfileEntity = modelMapper.map(requestBody, MemberProfileEntity.class);
        memberProfileEntity.setAddress(savedAddress);
        memberProfileEntity.setCompany(savedCompany);
        memberProfileEntity.setMember(memberEntity);

        MemberProfileEntity savedMemberProfile = memberProfileRepository.save(memberProfileEntity);

        memberEntity.setMemberProfile(savedMemberProfile);
        memberRepository.save(memberEntity);

        return modelMapper.map(savedMemberProfile, MemberProfileDto.class);
    }

    private CompanyEntity saveCompany(CompanyDto companyDto) {

        CompanyEntity companyEntity = modelMapper.map(companyDto, CompanyEntity.class);
        return companyRepository.save(companyEntity);
    }

    private AddressEntity saveAddress(AddressDto addressDto) {

//        final String url = "http://localhost:8080/api/addresses";
//
//        RestTemplate restTemplate = new RestTemplate();
//
//        Address savedAddress = restTemplate.postForObject(url, address, Address.class);

        Geo geo = addressDto.getGeo();

        GeoEntity geoEntity = geoRepository.save(modelMapper.map(geo, GeoEntity.class));

        AddressEntity addressEntity = modelMapper.map(addressDto, AddressEntity.class);
        addressEntity.setGeo(geoEntity);

        return addressRepository.save(addressEntity);
    }

    @Override
    public List<MemberProfileDto> getAll(PagingAndSorting ps) {
        return null;
    }

    @Override
    public MemberProfileDto getById(Long id) {
        return null;
    }

    @Override
    public MemberProfileDto updateById(Long id, MemberProfileDto updatedRequestBody) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public MemberProfileDto getLoggedInProfile() {

        MemberEntity memberEntity = authService.getLoggedInMember();

        return modelMapper.map(memberEntity.getMemberProfile(), MemberProfileDto.class);
    }
}
