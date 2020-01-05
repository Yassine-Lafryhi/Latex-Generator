import {PolymerElement} from '@polymer/polymer/polymer-element.js';
import {html} from '@polymer/polymer/lib/utils/html-tag.js';
import '@vaadin/vaadin-form-layout/vaadin-form-layout.js';
import '@vaadin/vaadin-form-layout/vaadin-form-item.js';
import '@vaadin/vaadin-text-field/vaadin-text-field.js';
import '@vaadin/vaadin-text-field/vaadin-text-area.js';
import '@vaadin/vaadin-button/vaadin-button.js';
import '@vaadin/vaadin-ordered-layout/vaadin-horizontal-layout.js';
import '@vaadin/vaadin-ordered-layout/vaadin-vertical-layout.js';

class AddExercise extends PolymerElement {
    static get template() {
        return html`

  <vaadin-vertical-layout theme="padding" id="all" style=" width: 80em;
    height: 80em;">
 
      <vaadin-vertical-layout id="wrapper" theme="padding" class="block">
  
       <h1 style="text-align:center">Add Exercise</h1>
       <vaadin-form-layout style="text-align:center">
       
       <vaadin-combo-box  label="Please select the school subject :"  id="combo" allow-custom-value>
       
</vaadin-combo-box>
        <br>
     <div theme="padding" style="text-align:center">
       <vaadin-form-layout style="text-align:center">
       <vaadin-combo-box label="Please select the exercise difficulty :"  id="combobox" allow-custom-value>
       
</vaadin-combo-box>
        <br>
<style>
  vaadin-text-area.min-height {
    min-height: 150px;
  }
</style>
 <br>
<vaadin-text-area class="min-height" id="textarea" label="Please enter the exercise content :" placeholder="Write here ...">
</vaadin-text-area>
      
         <vaadin-button id="Add" theme="success primary">Add</vaadin-button>
       </vaadin-form-layout>
      </vaadin-vertical-layout> 
      </vaadin-vertical-layout>
     
      
 <style>
 
  .block {
    display: flex;
    align-items: center;
    justify-content: center;
    width: 40em;
    height: 30em;
    border-radius: var(--lumo-border-radius);
    box-shadow: var(--lumo-box-shadow-s);
    padding: var(--lumo-space-s);
    
  }
  
  
</style>
`;
    }

    static get is() {
        return 'add-exercise';
    }

    static get properties() {
        return {
            // Declare your properties here.
        };
    }
}

customElements.define(AddExercise.is, AddExercise);
