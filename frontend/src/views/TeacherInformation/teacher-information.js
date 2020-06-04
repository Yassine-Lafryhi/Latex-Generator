import {PolymerElement} from '@polymer/polymer/polymer-element.js';
import {html} from '@polymer/polymer/lib/utils/html-tag.js';
import '@vaadin/vaadin-form-layout/vaadin-form-layout.js';
import '@vaadin/vaadin-form-layout/vaadin-form-item.js';
import '@vaadin/vaadin-text-field/vaadin-text-field.js';
import '@vaadin/vaadin-text-field/vaadin-text-area.js';
import '@vaadin/vaadin-button/vaadin-button.js';
import '@vaadin/vaadin-ordered-layout/vaadin-horizontal-layout.js';
import '@vaadin/vaadin-ordered-layout/vaadin-vertical-layout.js';

class TeacherInformation extends PolymerElement {
    static get template() {
        return html`
 


       <vaadin-vertical-layout theme="padding" id="all" style=" width: 80em;
    height: 80em;">
 
      <vaadin-vertical-layout id="wrapper" theme="padding" class="block">
       <h2>Teacher Information</h2>
       <vaadin-form-layout>
       
       <vaadin-text-field label="First Name :" id="FirstName" clear-button-visible value="Value"></vaadin-text-field>
       <vaadin-text-field label="Last Name :" id="LastName" clear-button-visible value="Value"></vaadin-text-field>
       <vaadin-email-field label="Email :" id="Email" name="email" error-message="Please enter a valid email address" clear-button-visible></vaadin-email-field>
<vaadin-password-field label="Password :" id="NewPassword" placeholder="Enter password" value="secret1"></vaadin-password-field>
<vaadin-password-field label="Repeat Password :"  id="RepeatPassword" placeholder="Enter password" value="secret1"></vaadin-password-field>
<vaadin-text-field label="University Name :" id="UniversityName" clear-button-visible value="Value"></vaadin-text-field>

<vaadin-text-field label="Establishment Name :" id="EstablishmentName" clear-button-visible value="Value"></vaadin-text-field>
    
       <vaadin-button id="Save" theme="success primary">Save</vaadin-button>
       
   
       
 
       </vaadin-form-layout>

      </vaadin-vertical-layout>
         </vaadin-vertical-layout>
  <style>
 
  .block {
    display: flex;
    align-items: center;
    justify-content: center;
    width: 40em;
    height: 42em;
    border-radius: var(--lumo-border-radius);
    box-shadow: var(--lumo-box-shadow-s);
    padding: var(--lumo-space-s);
    
  }
  
  
</style>
`;
    }

    static get is() {
        return 'teacher-information';
    }

    static get properties() {
        return {
            // Declare your properties here.
        };
    }
}

customElements.define(TeacherInformation.is, TeacherInformation);
