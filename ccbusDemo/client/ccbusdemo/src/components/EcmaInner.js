import {ExtComponent} from 'ccbus';
import React from 'react';
import {PropTypes} from 'prop-types';

export class EcmaInner extends ExtComponent
{
    
    /**
    * @returns {}
    */ 
    constructor()
    {
        super();
        this.onChangeProp("propName",(prop,propPrev) => 
        {
            this.stateKey("stateName",prop);
            return 0;
        });
    }
    
    /**
    * @type {Object}
    */ 
    state = {
        
        /**
        * @type {String}
        */ 
        stateName: ""
    };
    static 
    /**
    * @type {Object}
    */ 
    propTypes = {
        
        /**
        * @type {String}
        */ 
        propName: PropTypes.string
    };
    static 
    /**
    * @type {Object}
    */ 
    defaultProps = {
        
        /**
        * @type {String}
        */ 
        propName: ""
    };
    
    /**
    * @type {Function<Integer,Integer>}
    */ 
    handleOnChangeD = (b) => 
    {
        return b+1;
    };
    
    render()
    {
        return <div>Inner says: Prop:{this.props.propName}<br/>State:{this.state.stateName}</div>
    }
}