/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sis.controller;

import sis.model.Settings;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import javax.transaction.UserTransaction;

/**
 *
 * @author Veekija
 */
@ManagedBean
public class SettingsCRUDController {

    @PersistenceUnit(unitName = "SISPU")
    private EntityManagerFactory entityManagerFactory;
    @Resource
    private UserTransaction userTransaction;
    private List<Settings> settingsList;
    @ManagedProperty(value = "#{settings}")
    private Settings settings;

    @PostConstruct
    public void init() {
        retrieveSettingss();
    }

    private void retrieveSettingss() {
        try {
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            String queryString = "select s from Settings s";
            Query query = entityManager.createQuery(queryString);
            this.setSettingsList((List<Settings>) query.getResultList());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String createSettings() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            userTransaction.begin();
            entityManager.persist(getSettings());
            userTransaction.commit();
            retrieveSettingss();
            return "/admin/settingsCRUD";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    public String addSettings() {
        return "/admin/settingsCreate";
    }

    public String updateSettings() {
        try {
            EntityManager em = entityManagerFactory.createEntityManager();
            userTransaction.begin();
            Settings s = em.find(Settings.class, this.settings.getSettingId());
            s.setSettingName(this.settings.getSettingName());
            s.setSettingValue(this.settings.getSettingValue());
            em.persist(s);
            userTransaction.commit();
            retrieveSettingss();
            return "/admin/settingsCRUD";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    public String deleteSettings(Settings argSettings) {
        try {
            EntityManager em = entityManagerFactory.createEntityManager();
            userTransaction.begin();
            Settings currentSettings = em.find(Settings.class, argSettings.getSettingId());
            em.remove(currentSettings);
            userTransaction.commit();
            retrieveSettingss();
            return "/admin/settingsCRUD";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    public String editSettings(Settings argSettings) {
        this.settings = argSettings;
        return "/admin/settingsUpdate";
    }

    /**
     * @return the settingss
     */
    public List<Settings> getSettingsList() {
        return settingsList;
    }

    /**
     * @param settingss the settingss to set
     */
    public void setSettingsList(List<Settings> settingss) {
        this.settingsList = settingss;
    }

    /**
     * @return the settings
     */
    public Settings getSettings() {
        return settings;
    }

    /**
     * @param settings the settings to set
     */
    public void setSettings(Settings settings) {
        this.settings = settings;
    }
}
