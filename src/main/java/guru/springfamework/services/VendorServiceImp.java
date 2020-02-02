package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.VendorMapper;
import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.domain.Vendor;
import guru.springfamework.repositories.VendorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VendorServiceImp implements VendorService {
    VendorRepository vendorRepository;
    VendorMapper vendorMapper;

    public VendorServiceImp(VendorRepository vendorRepository, VendorMapper vendorMapper) {
        this.vendorRepository = vendorRepository;
        this.vendorMapper = vendorMapper;
    }

    @Override
    public List<VendorDTO> getAllVendors() {
        return vendorRepository.findAll().stream().
                map(vendor -> {
                    VendorDTO vendorDTO = vendorMapper.vendorToVendorDTO(vendor);
                    vendorDTO.setCustomUrl("/api/v1/vendor/" + vendor.getId());
                    return vendorDTO;
                }).collect(Collectors.toList());
    }

    @Override
    public VendorDTO getVendorById(Long id) {
        return vendorRepository.findById(id).map(vendorMapper::vendorToVendorDTO).
                orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public VendorDTO createNewVendor(VendorDTO vendorDTO) {
        Vendor vendor = vendorMapper.vendorDTOtoVendor(vendorDTO);
        return saveVendorAndReturnVendorDTO(vendor);
    }

    @Override
    public VendorDTO saveVendorDTO(Long id, VendorDTO vendorDTO) {
        Vendor vendor = vendorMapper.vendorDTOtoVendor(vendorDTO);
        vendor.setId(id);

        return saveVendorAndReturnVendorDTO(vendor);
    }

    @Override
    public VendorDTO patchVendorDTO(Long id, VendorDTO vendorDTO) {
        return vendorRepository.findById(id).map(vendor -> {
            if (vendorDTO.getName() != null) {
                vendor.setName(vendorDTO.getName());
            }

            VendorDTO returnedVendorDTO = vendorMapper.vendorToVendorDTO(vendor);
            vendorDTO.setCustomUrl("/api/v1/vendor/" + vendor.getId());

            return returnedVendorDTO;
        }).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public void deleteVendorById(Long id) {
        vendorRepository.deleteById(id);
    }

    private VendorDTO saveVendorAndReturnVendorDTO(Vendor vendor) {
        Vendor savedVendor = vendorRepository.save(vendor);
        VendorDTO savedVendorDTO = vendorMapper.vendorToVendorDTO(savedVendor);
        savedVendorDTO.setCustomUrl("/api/v1/vendor/" + savedVendor.getId());
        return savedVendorDTO;
    }
}
