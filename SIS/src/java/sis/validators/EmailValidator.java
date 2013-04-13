/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sis.validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlInputText;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author Veekija
 */
@FacesValidator(value = "emailValidator")
public class EmailValidator implements Validator {

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String emailAddress = (String) value;
        if (!"".equalsIgnoreCase(emailAddress)) {
            HtmlInputText htmlInputText = (HtmlInputText) component;
            String pattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
            //if (!emailAddress.matches("[A-Za-z0-9]+@[A-Za-z0-9]+.[A-Za-z0-9]+")) {
            if (!emailAddress.matches(pattern)) {
                FacesMessage facesMessage = new FacesMessage(htmlInputText.getLabel() + ": email format is not valid");
                throw new ValidatorException(facesMessage);
            }
        }
    }
}
