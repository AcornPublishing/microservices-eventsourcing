import{S as l,i as m,a as b,b as c,l as d,s as u,f,n as s,h,m as p,c as g}from"./index.239d54ae.js";function j(o){let e,n,t;return{c(){e=f("button"),e.textContent=`Add to Cart${o[0].prodId}`,this.c=s,h(e,"class","btn btn-primary btn-sm")},m(r,a){c(r,e,a),n||(t=p(e,"click",o[1]),n=!0)},p:s,i:s,o:s,d(r){r&&g(e),n=!1,t()}}}function k(o,e,n){let{itemjson:t}=e,r=t?JSON.parse(t):{};function a(){console.log(r),alert(r.prodId)}return o.$$set=i=>{"itemjson"in i&&n(2,t=i.itemjson)},[r,a,t]}class x extends l{constructor(e){super(),this.shadowRoot.innerHTML=`<style>.btn{display:inline-block;font-weight:400;line-height:1.5;color:#212529;text-align:center;text-decoration:none;vertical-align:middle;cursor:pointer;-webkit-user-select:none;-moz-user-select:none;user-select:none;background-color:transparent;border:1px solid transparent;padding:.375rem .75rem;font-size:1rem;border-radius:.25rem;transition:color .15s ease-in-out, background-color .15s ease-in-out, border-color .15s ease-in-out, box-shadow .15s ease-in-out
    }@media(prefers-reduced-motion:reduce){.btn{transition:none
        }}.btn:hover{color:#212529
    }.btn:focus{outline:0;box-shadow:0 0 0 .25rem rgba(13, 110, 253, .25)
    }.btn:disabled{pointer-events:none;opacity:.65
    }.btn-primary{color:#fff;background-color:#0d6efd;border-color:#0d6efd
    }.btn-primary:hover{color:#fff;background-color:#0b5ed7;border-color:#0a58ca
    }.btn-primary:focus{color:#fff;background-color:#0b5ed7;border-color:#0a58ca;box-shadow:0 0 0 .25rem rgba(49, 132, 253, .5)
    }.btn-sm{padding:.25rem .5rem;font-size:.875rem;border-radius:.2rem
    }</style>`,m(this,{target:this.shadowRoot,props:b(this.attributes),customElement:!0},k,j,u,{itemjson:2},null),e&&(e.target&&c(e.target,this,e.anchor),e.props&&(this.$set(e.props),d()))}static get observedAttributes(){return["itemjson"]}get itemjson(){return this.$$.ctx[2]}set itemjson(e){this.$$set({itemjson:e}),d()}}customElements.define("cart-additem",x);
