package guru.springfamework.controllers.v1;

import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.api.v1.model.VendorListDTO;
import guru.springfamework.services.VendorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"vendorControllerTag"})
@RestController
@RequestMapping("/api/v1/vendors/")
public class VendorController {
    private final VendorService vendorService;

    public VendorController(VendorService vendorService) {
        this.vendorService = vendorService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Returns the list of vendors", notes = "Some api notes")
    public VendorListDTO getAllCustomers() {
        return new VendorListDTO(vendorService.getAllVendors());
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Returns the vendor by Name", notes = "Some api notes")
    public VendorDTO getCategoryByName(@PathVariable String id) {
        return vendorService.getVendorById(Long.valueOf(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Creates the new vendor", notes = "Some api notes")
    public VendorDTO createNewCustomer(@RequestBody VendorDTO vendorDTO) {
        return vendorService.createNewVendor(vendorDTO);
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Updates the vendors", notes = "Some api notes")
    public VendorDTO updateCustomer(@PathVariable Long id, @RequestBody VendorDTO vendorDTO) {
        return vendorService.saveVendorDTO(id, vendorDTO);
    }

    @PatchMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Patches the vendor", notes = "Some api notes")
    public VendorDTO patchCustomer(@PathVariable Long id, @RequestBody VendorDTO vendorDTO) {
        return vendorService.patchVendorDTO(id, vendorDTO);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Deletes the vendor", notes = "Some api notes")
    public void deleteCustomer(@PathVariable Long id) {
        vendorService.deleteVendorById(id);
    }
}
