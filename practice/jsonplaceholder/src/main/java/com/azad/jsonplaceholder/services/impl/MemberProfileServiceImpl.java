package com.azad.jsonplaceholder.services.impl;

import com.azad.jsonplaceholder.models.Address;
import com.azad.jsonplaceholder.models.Company;
import com.azad.jsonplaceholder.models.Geo;
import com.azad.jsonplaceholder.models.dtos.AddressDto;
import com.azad.jsonplaceholder.models.dtos.MemberProfileDto;
import com.azad.jsonplaceholder.models.entities.AddressEntity;
import com.azad.jsonplaceholder.models.entities.CompanyEntity;
import com.azad.jsonplaceholder.models.entities.GeoEntity;
import com.azad.jsonplaceholder.models.entities.MemberProfileEntity;
import com.azad.jsonplaceholder.repos.MemberProfileRepository;
import com.azad.jsonplaceholder.security.auth.api.AuthService;
import com.azad.jsonplaceholder.security.entities.MemberEntity;
import com.azad.jsonplaceholder.services.MemberProfileService;
import com.azad.jsonplaceholder.utils.AppUtils;
import com.azad.jsonplaceholder.utils.PagingAndSorting;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MemberProfileServiceImpl implements MemberProfileService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AppUtils appUtils;

    @Autowired
    private AuthService authService;



    private final MemberProfileRepository memberProfileRepository;

    @Autowired
    public MemberProfileServiceImpl(MemberProfileRepository memberProfileRepository) {
        this.memberProfileRepository = memberProfileRepository;
    }

    @Override
    public MemberProfileDto create(MemberProfileDto requestBody) {

        MemberEntity loggedInMember = authService.getLoggedInMember();

        MemberProfileEntity memberProfileEntity = modelMapper.map(requestBody, MemberProfileEntity.class);
        memberProfileEntity.setMemberEntity(loggedInMember);

        MemberProfileEntity savedMemberProfile = memberProfileRepository.save(memberProfileEntity);

        return modelMapper.map(savedMemberProfile, MemberProfileDto.class);
    }

    @Override
    public List<MemberProfileDto> getAll(PagingAndSorting ps) {

        if (!authService.loggedInUserIsAdmin()) {
            throw new RuntimeException("Only admins can access this api");
        }

        Pageable pageable;
        if (ps.getSort().isEmpty())
            pageable = PageRequest.of(ps.getPage(), ps.getLimit());
        else
            pageable = PageRequest.of(ps.getPage(), ps.getLimit(), appUtils.getSortAndOrder(ps.getSort(), ps.getOrder()));

        List<MemberProfileEntity> allMemberProfilesFromDb = memberProfileRepository.findAll(pageable).getContent();
        if (allMemberProfilesFromDb.size() == 0)
            return null;

        return allMemberProfilesFromDb.stream()
                .map(memberProfileEntity -> modelMapper.map(memberProfileEntity, MemberProfileDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public MemberProfileDto getById(Long id) {

        if (!authService.loggedInUserIsAdmin()) {
            throw new RuntimeException("Only admins can access this api");
        }

        MemberProfileEntity memberProfileFromDb = memberProfileRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Profile not found with id: " + id));

        return modelMapper.map(memberProfileFromDb, MemberProfileDto.class);
    }

    @Override
    public MemberProfileDto updateById(Long id, MemberProfileDto updatedRequestBody) {

        if (!authService.loggedInUserIsAdmin()) {
            throw new RuntimeException("Only admins can access this api");
        }

        MemberProfileEntity memberProfileFromDb = memberProfileRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Profile not found with id: " + id));

        if (updatedRequestBody.getPhone() != null)
            memberProfileFromDb.setPhone(updatedRequestBody.getPhone());
        if (updatedRequestBody.getWebsite() != null)
            memberProfileFromDb.setWebsite(updatedRequestBody.getWebsite());
        if (updatedRequestBody.getAddress() != null) {
            AddressEntity addressEntity = memberProfileFromDb.getAddress();

            Address updatedAddress = updatedRequestBody.getAddress();
            if (updatedAddress.getStreet() != null)
                addressEntity.setStreet(updatedAddress.getStreet());
            if (updatedAddress.getCity() != null)
                addressEntity.setCity(updatedAddress.getCity());
            if (updatedAddress.getSuite() != null)
                addressEntity.setSuite(updatedAddress.getSuite());
            if (updatedAddress.getZipcode() != null)
                addressEntity.setZipcode(updatedAddress.getZipcode());
            if (updatedAddress.getGeo() != null) {
                GeoEntity geoEntity = memberProfileFromDb.getAddress().getGeo();

                Geo geo = updatedAddress.getGeo();
                if (geo.getLat() != 0)
                    geoEntity.setLat(geo.getLat());
                if (geo.getLng() != 0)
                    geoEntity.setLng(geo.getLng());

                addressEntity.setGeo(geoEntity);
            }
            memberProfileFromDb.setAddress(addressEntity);
        }
        if (updatedRequestBody.getCompany() != null) {
            CompanyEntity companyEntity = memberProfileFromDb.getCompany();

            Company updatedCompany = updatedRequestBody.getCompany();
            if (updatedCompany.getName() != null)
                companyEntity.setName(updatedCompany.getName());
            if (updatedCompany.getCatchPhrase() != null)
                companyEntity.setCatchPhrase(updatedCompany.getCatchPhrase());
            if (updatedCompany.getBs() != null)
                companyEntity.setBs(updatedCompany.getBs());

            memberProfileFromDb.setCompany(companyEntity);
        }

        MemberProfileEntity updatedMemberProfile = memberProfileRepository.save(memberProfileFromDb);

        return modelMapper.map(updatedMemberProfile, MemberProfileDto.class);
    }

    @Override
    public void deleteById(Long id) {

        if (!authService.loggedInUserIsAdmin()) {
            throw new RuntimeException("Only admins can access this api");
        }

        MemberProfileEntity memberProfileFromDb = memberProfileRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Profile not found with id: " + id));

        memberProfileRepository.delete(memberProfileFromDb);
    }


}
