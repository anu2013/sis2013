/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sis.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

/**
 *
 * @author Anupama Karumudi
 */
@Entity
@Table(name = "CONVERSATIONS")
@NamedQueries({
    @NamedQuery(name = "Conversations.findAll", query = "SELECT c FROM Conversations c")})
public class Conversations implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "CONVERSATIONID")
    private Integer conversationid;
    @Size(max = 255)
    @Column(name = "CONVERSATIONTEXT")
    private String conversationtext;
    @Column(name = "SENTDATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date sentdate;
    @JoinColumn(name = "SENTBY", referencedColumnName = "USERID")
    @ManyToOne
    private Users sentby;
    @JoinColumn(name = "MESSAGEID", referencedColumnName = "MESSAGEID")
    @ManyToOne
    private Messages messageid;
    @OneToMany(mappedBy = "parentconversationid")
    private List<Conversations> conversationsList;
    @JoinColumn(name = "PARENTCONVERSATIONID", referencedColumnName = "CONVERSATIONID")
    @ManyToOne
    private Conversations parentconversationid;

    public Conversations() {
    }

    public Conversations(Integer conversationid) {
        this.conversationid = conversationid;
    }

    public Integer getConversationid() {
        return conversationid;
    }

    public void setConversationid(Integer conversationid) {
        this.conversationid = conversationid;
    }

    public String getConversationtext() {
        return conversationtext;
    }

    public void setConversationtext(String conversationtext) {
        this.conversationtext = conversationtext;
    }

    public Date getSentdate() {
        return sentdate;
    }

    public void setSentdate(Date sentdate) {
        this.sentdate = sentdate;
    }

    public Users getSentby() {
        return sentby;
    }

    public void setSentby(Users sentby) {
        this.sentby = sentby;
    }

    public Messages getMessageid() {
        return messageid;
    }

    public void setMessageid(Messages messageid) {
        this.messageid = messageid;
    }

    public List<Conversations> getConversationsList() {
        return conversationsList;
    }

    public void setConversationsList(List<Conversations> conversationsList) {
        this.conversationsList = conversationsList;
    }

    public Conversations getParentconversationid() {
        return parentconversationid;
    }

    public void setParentconversationid(Conversations parentconversationid) {
        this.parentconversationid = parentconversationid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (conversationid != null ? conversationid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Conversations)) {
            return false;
        }
        Conversations other = (Conversations) object;
        if ((this.conversationid == null && other.conversationid != null) || (this.conversationid != null && !this.conversationid.equals(other.conversationid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sis.model.Conversations[ conversationid=" + conversationid + " ]";
    }
    
}
