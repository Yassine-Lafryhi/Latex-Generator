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
<center>
<div class="content">
      <custom-style>
        <style include="shared-styles">
          :host {
              display: block;
              padding: 1rem;
          }
          .content {
  max-width: 500px;
  margin: auto;
        </style>
      </custom-style>

      <vaadin-vertical-layout id="wrapper" theme="padding" style="text-align:center">
       <h1 style="text-align:center">Add Exercise</h1>
       <vaadin-form-layout style="text-align:center">
       <vaadin-combo-box id="combo" allow-custom-value>
       
</vaadin-combo-box>
        <br>
        <vaadin-combo-box id="combobox" allow-custom-value>
       
</vaadin-combo-box>
<br>
<vaadin-text-field label="Easy"></vaadin-text-field>
<vaadin-combo-box id="easy" allow-custom-value>
       
</vaadin-combo-box>
<br>
<vaadin-text-field label="Medium"></vaadin-text-field>
<vaadin-combo-box id="medium" allow-custom-value>
       
</vaadin-combo-box>
<br>
<vaadin-text-field label="difficult" value=""></vaadin-text-field>
<vaadin-combo-box id="difficult" allow-custom-value>
       
</vaadin-combo-box>

 <br>
 
 <vaadin-text-field autoselect label="niveau" value="" id="niveau"></vaadin-text-field>
 <br>
  <vaadin-text-field autoselect label="duree" value=""id="duree"></vaadin-text-field>
 <br>
       </vaadin-form-layout>
       <vaadin-horizontal-layout theme="spacing" style="display:flex;flex-wrap:wrap-reverse;width:100%;justify-content:flex-end;">
        <vaadin-button theme="tertiary" id="Pdf" slot="" style="text-align:center">
         GeneratePDF
        </vaadin-button>
        <vaadin-button theme="primary" id="Latex" style="text-align:center">
         GenerateLatex
        </vaadin-button>
       </vaadin-horizontal-layout>
      </vaadin-vertical-layout>
      
    </div>
    </center>
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
