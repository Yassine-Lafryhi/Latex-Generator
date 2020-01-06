import { PolymerElement } from '@polymer/polymer/polymer-element.js';
import { html } from '@polymer/polymer/lib/utils/html-tag.js';
import '@vaadin/vaadin-form-layout/vaadin-form-layout.js';
import '@vaadin/vaadin-form-layout/vaadin-form-item.js';
import '@vaadin/vaadin-text-field/vaadin-text-field.js';
import '@vaadin/vaadin-text-field/vaadin-text-area.js';
import '@vaadin/vaadin-button/vaadin-button.js';
import '@vaadin/vaadin-ordered-layout/vaadin-horizontal-layout.js';
import '@vaadin/vaadin-ordered-layout/vaadin-vertical-layout.js';

class LatexGenerator extends PolymerElement {
    static get template() {
        return html`
 <vaadin-vertical-layout theme="padding" id="all">

      <vaadin-vertical-layout id="wrapper" theme="padding" class="block">
       <h2 style="text-align:center">Latex Generator</h2>
       <vaadin-form-layout style="text-align:center">

    <vaadin-combo-box required  label="Please select the school subject :"  id="SchoolSubject" allow-custom-value></vaadin-combo-box>
    
     <vaadin-combo-box  required label="Please select the test type :"  id="TestType" allow-custom-value></vaadin-combo-box>


 <vaadin-text-field required autoselect label="Please enter the test title :" value="" id="TestTitle"></vaadin-text-field>

<vaadin-integer-field required id="easy" min="0" max="10" label="How many easy exercises do you want :" has-controls></vaadin-integer-field>
<vaadin-integer-field required id="medium" min="0" max="10" label="How many medium exercises do you want :" has-controls></vaadin-integer-field>
<vaadin-integer-field required id="difficult" min="0" max="10" label="How many difficult exercises do you want :" has-controls></vaadin-integer-field>
</vaadin-combo-box>

 
 <vaadin-text-field  required autoselect label="Please enter the grade :" value="" id="Grade"></vaadin-text-field>

  <vaadin-text-field required autoselect label="Please enter the test duration :" value="" id="TestDuration"></vaadin-text-field>


  <vaadin-button id="GeneratePDF" theme="error primary">Generate PDF File</vaadin-button>
  
  
    <vaadin-button id="GenerateLATEX" theme="success primary">Generate LATEX File</vaadin-button>


       </vaadin-form-layout>
 
      </vaadin-vertical-layout>
      
            </vaadin-vertical-layout>
      
  
    
    
      <style>
 
  .block {
    display: flex;
    align-items: center;
    justify-content: center;
    width: 40em;
    height: 46em;
    border-radius: var(--lumo-border-radius);
    box-shadow: var(--lumo-box-shadow-s);
    padding: var(--lumo-space-s);
    
  }
  
  
</style>
`;
    }

    static get is() {
        return 'latex-generator';
    }

    static get properties() {
        return {
            // Declare your properties here.
        };
    }
}

customElements.define(LatexGenerator.is,LatexGenerator);
