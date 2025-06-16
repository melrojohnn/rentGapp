package com.melro.rentapp.model;

import com.melro.rentapp.model.common.RentableItem;
import jakarta.persistence.*;

@Entity
@Table(name = "tb_smartphone")
public class SmartphoneModel extends RentableItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "screen", length = 100)
    private String screen;

    @Column(name = "camera", length = 50)
    private String camera;

    @Column(name = "hd", length = 50)
    private String hd;

    public SmartphoneModel() {}

    public SmartphoneModel(String model, String brand, String screen, String camera, String hd) {
        this.model = model;
        this.brand = brand;
        this.screen = screen;
        this.camera = camera;
        this.hd = hd;
    }

    public Long getId() {
        return id;
    }

    public String getScreen() {
        return screen;
    }

    public void setScreen(String screen) {
        this.screen = screen;
    }

    public String getCamera() {
        return camera;
    }

    public void setCamera(String camera) {
        this.camera = camera;
    }

    public String getHd() {
        return hd;
    }

    public void setHd(String hd) {
        this.hd = hd;
    }
}
