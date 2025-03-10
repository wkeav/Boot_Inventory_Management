package com.Wkea.boots.controller;

import com.Wkea.boots.entities.Boot;
import com.Wkea.boots.enums.BootType;
import com.Wkea.boots.repositories.BootRepository;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Wkea.boots.exceptions.QueryNotSupportedException;

@RestController
@RequestMapping("/api/v1/boots")
public class BootController {

private final BootRepository bootRepository; 
public BootController(final BootRepository bootRepository){
    this.bootRepository = bootRepository;
}


	@GetMapping("/")
	public Iterable<Boot> getAllBoots() {
		return this.bootRepository.findAll();
	}

	@GetMapping("/types/")
	public List<String> getBootTypes() {
    return Arrays.stream(BootType.values())  // Convert the enum array to a stream
                 .map(Enum::name)            // Map each enum to its name
                 .collect(Collectors.toList());  // Collect the results into a List
}


	@PostMapping("/")
	public Boot addBoot(@RequestBody Boot boot) {
		return this.bootRepository.save(boot);
	}

	@DeleteMapping("/{id}")
	public Boot deleteBoot(@PathVariable("id") Integer id) {
		Optional <Boot> bootToDeleteOptional = this.bootRepository.findById(id);
		if(!bootToDeleteOptional.isPresent()){
			return null;
		}
		Boot bootToDelete = bootToDeleteOptional.get();
		this.bootRepository.delete(bootToDelete);
		return bootToDelete;
	}

	@PutMapping("/{id}/quantity/increment")
	public Boot incrementQuantity(@PathVariable("id") Integer id) {
		Optional<Boot> bootIncrementQuantityOptional = this.bootRepository.findById(id);
		if(!bootIncrementQuantityOptional.isPresent()){
			return null;
		}
		Boot bootIncrement =  bootIncrementQuantityOptional.get();
		bootIncrement.setQuantity(bootIncrement.getQuantity()+ 1 );
		this.bootRepository.save(bootIncrement);
		return bootIncrement;

	}

	@PutMapping("/{id}/quantity/decrement")
	public Boot decrementQuantity(@PathVariable("id") Integer id) {
		Optional <Boot> bootDecrementQuantity =  this.bootRepository.findById(id);
		if (!bootDecrementQuantity.isPresent()) {
			return null;
		}
		Boot bootDecrement = bootDecrementQuantity.get();
		bootDecrement.setQuantity(bootDecrement.getQuantity() - 1);
		this.bootRepository.save(bootDecrement);
		return bootDecrement;
	}

	@GetMapping("/search")
	public List<Boot> searchBoots(@RequestParam(required = false) String material,
			@RequestParam(required = false) BootType type, @RequestParam(required = false) Float size,
			@RequestParam(required = false, name = "quantity") Integer minQuantity) throws QueryNotSupportedException {
		if (Objects.nonNull(material)) {
			if (Objects.nonNull(type) && Objects.nonNull(size) && Objects.nonNull(minQuantity)) {
				// call the repository method that accepts a material, size, type, and minimum
				// quantity
				return this.bootRepository.findByMaterialAndSizeAndTypeAndQuantityGreaterThan(material, size, type, minQuantity);
			} else if (Objects.nonNull(type) && Objects.nonNull(size)) {
				// call the repository method that accepts a material, size, and type
				return this.bootRepository.findByMaterialAndSizeAndType(material, size, type);
			} else if (Objects.nonNull(type) && Objects.nonNull(minQuantity)) {
				// call the repository method that accepts a material, a type, and a minimum
				// quantity
				return this.bootRepository.findByMaterialAndTypeAndQuantityGreaterThan(material, type, minQuantity);
			} else if (Objects.nonNull(type)) {
				// call the repository method that accepts a material and a type
				return this.bootRepository.findByMaterialAndType(material, type);
			} else {
				// call the repository method that accepts only a material
				return this.bootRepository.findByMaterial(material);
			}
		} else if (Objects.nonNull(type)) {
			if (Objects.nonNull(size) && Objects.nonNull(minQuantity)) {
				// call the repository method that accepts a type, size, and minimum quantity
				return this.bootRepository.findByTypeAndSizeAndQuantityGreaterThan(type, size, minQuantity);
			} else if (Objects.nonNull(size)) {
				// call the repository method that accepts a type and a size
				return this.bootRepository.findByTypeAndSize(type, size);
			} else if (Objects.nonNull(minQuantity)) {
				// call the repository method that accepts a type and a minimum quantity
				return this.bootRepository.findByTypeAndQuantityGreaterThan(type, minQuantity);
			} else {
				// call the repository method that accept only a type
				return this.bootRepository.findByType(type);
			}
		} else if (Objects.nonNull(size)) {
			if (Objects.nonNull(minQuantity)) {
				// call the repository method that accepts a size and a minimum quantity
				return this.bootRepository.findBySizeAndQuantityGreaterThan(size, minQuantity);
			} else {
				// call the repository method that accepts only a size
				return this.bootRepository.findBySize(size);
			}
		} else if (Objects.nonNull(minQuantity)) {
			// call the repository method that accepts only a minimum quantity
			return this.bootRepository.findByQuantityGreaterThan(minQuantity);
		} else {
			throw new QueryNotSupportedException("This query is not supported! Try a different combination of search parameters.");
		}
	}
	
}
//
