package com.melro.rentapp.model;

import com.melro.rentapp.model.common.RentableItem;
import jakarta.persistence.*;

@Entity
@Table(name = "tb_laptop")
public class LaptopModel extends RentableItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long laptopId;

    @Column(name = "gpu", length = 100)
    private String gpu;

    @Column(name = "cpu", length = 100)
    private String cpu;

    @Column(name = "ram", length = 50)
    private String ram;

    @Column(name = "hd", length = 50)
    private String hd;

    public LaptopModel() {
    }

    public LaptopModel(String model, String brand, String gpu, String cpu, String ram, String hd) {
        this.model = model;
        this.brand = brand;
        this.gpu = gpu;
        this.cpu = cpu;
        this.ram = ram;
        this.hd = hd;
    }

    public Long getLaptopId() {
        return laptopId;
    }

    public void setLaptopId(Long laptopId) {
        this.laptopId = laptopId;
    }

    public String getGpu() {
        return gpu;
    }

    public void setGpu(String gpu) {
        this.gpu = gpu;
    }

    public String getCpu() {
        return cpu;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    public String getRam() {
        return ram;
    }

    public void setRam(String ram) {
        this.ram = ram;
    }

    public String getHd() {
        return hd;
    }

    public void setHd(String hd) {
        this.hd = hd;
    }
}
