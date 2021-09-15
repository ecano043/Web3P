package ar.edu.iua.iw3.modelo.persistencia;

import java.util.Optional;
import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.edu.iua.iw3.modelo.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
	List<Producto> findByDescripcion(String d);
	List<Producto> findByDescripcionOrderByPrecio(String d);
	
	List<Producto> findByPrecio(double p);
	List<Producto> findByPrecioOrderByDescripcionDesc(double p);
	List<Producto> findByPrecioBetween(double precio1, double precio2);
	List<Producto> findByFechaVencimientoIsNotNull();
	List<Producto> findByFechaVencimientoAfter(Date fecha);
	List<Producto> findFirst2ByFechaVencimiento(Date f1, Date f2);
} 
