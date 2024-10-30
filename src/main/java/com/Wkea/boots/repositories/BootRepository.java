package com.Wkea.boots.repositories;

import java.util.List;

import com.Wkea.boots.entities.Boot;
import org.springframework.data.repository.CrudRepository;
import com.Wkea.boots.enums.BootType;


public interface BootRepository extends CrudRepository<Boot, Integer> {
    
    public List<Boot> findBySize(Float size);
    public List<Boot> findByMaterial(String material);
    public List<Boot> findByType(BootType type);
    public List<Boot> findByQuantityGreaterThan(Integer minQuantity);
    public List<Boot> findByMaterialAndSize(String material, Float size);
    public List<Boot> findByMaterialAndQuantityGreaterThan(String material,Integer minQuantity);
    public List<Boot> findByMaterialAndType(String material, BootType type);
    public List<Boot> findByMaterialAndSizeAndType(String material, Float size ,BootType type );
    public List<Boot> findByMaterialAndTypeAndQuantityGreaterThan(String material,BootType type, Integer minQuantity );
    public List<Boot> findByMaterialAndSizeAndTypeAndQuantityGreaterThan(String material, Float size, BootType type,Integer minQuantity );
    public List<Boot> findByTypeAndSize(BootType type, Float size);
    public List<Boot> findByTypeAndSizeAndQuantityGreaterThan(BootType type, Float size,Integer minQuantity);
    public List<Boot> findByTypeAndQuantityGreaterThan(BootType type,Integer minQuantity );
    public List<Boot> findBySizeAndQuantityGreaterThan(Float size,Integer minQuantity ); 
}
