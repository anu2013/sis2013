/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sis.controller;

import sis.model.Settings;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
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
    private String propsectiveStudentsText;

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
        EntityManager entityManager = null;
        List<Settings> setList = null;
        String settingName = getSettings().getSettingName();
        if ("0".equalsIgnoreCase(settingName)) {
            setInfoMessage("Please select valid content page.");
            return null;
        }

        String settingValue = getSettings().getSettingValue();
        if ("".equalsIgnoreCase(settingValue)) {
            setInfoMessage("Content Description should not be empty.");
            return null;
        }

        setList = retrieveSettingsBySettingName(settingName);
        if (!setList.isEmpty()) {
            setInfoMessage("The content text for the selected page already exists. "
                    + "Please use Edit option to update or delete option to delete and recreate.");
            return null;
        }
        try {
            entityManager = entityManagerFactory.createEntityManager();
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

    private List retrieveSettingsBySettingName(String argSettingName) {
        EntityManager entityManager = null;
        List<Settings> setList = null;
        entityManager = entityManagerFactory.createEntityManager();
        String queryString = "select s from Settings s where s.settingName=:settingName";
        Query query = entityManager.createQuery(queryString);
        query.setParameter("settingName", argSettingName);
        setList = (List<Settings>) query.getResultList();
        return setList;
    }

    public String addSettings() {
        return "/admin/settingsCreate";
    }

    public String updateSettings() {
        try {
            EntityManager em = entityManagerFactory.createEntityManager();
            userTransaction.begin();
            Settings s = em.find(Settings.class, this.settings.getSettingId());
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

    protected void setInfoMessage(String summary) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null));
    }

    /**
     * @return the propsectiveStudentsText
     */
    public String getPropsectiveStudentsText() {
        List<Settings> setList = retrieveSettingsBySettingName("Prospective Students");
        if (!setList.isEmpty()) {
            for (Settings set : setList){
                propsectiveStudentsText = set.getSettingValue();
            }
        }
        return propsectiveStudentsText;
    }

    /**
     * @param propsectiveStudentsText the propsectiveStudentsText to set
     */
    public void setPropsectiveStudentsText(String propsectiveStudentsText) {
        this.propsectiveStudentsText = propsectiveStudentsText;
    }
}
