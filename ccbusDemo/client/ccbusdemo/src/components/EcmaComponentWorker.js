import {Error} from 'ccbus';
import {WorkerServer} from 'ccbus';
import {Pager} from 'ccbus/components/Pager';
import {SomePayload} from 'payload/SomePayload';
import {OtherPayload} from 'payload/packp/OtherPayload';
import {EcmaInner} from 'components/EcmaInner';
import React from 'react';

import {ExtComponent} from 'ccbus';
import {PageData} from 'ccbus/payload/PageData';

import {ServiceSome} from 'service/ServiceSome';

export class EcmaComponentWorker extends Pager
{
    
    /**
    * @returns {}
    */ 
    constructor()
    {
        super();
    }
    
    /**
    * @type {Object}
    */ 
    state = {
        
        /**
        * @type {String}
        */ 
        name: "Some Vla",
        
        /**
        * @type {Array<String>}
        */ 
        a: new Array(),
        
        /**
        * @type {Array<Integer>}
        */ 
        aa: new Array()
    };
    
    /**
    * @type {Date}
    */ 
    date = new Date("2012-01-02 13:00");
    
    /**
    * @type {Date}
    */ 
    dateTime = new Date("2012-01-02 13:00");
    
    /**
    * @type {String}
    */ 
    nameB = "Some Vla";
    
    /**
    * @type {SomePayload}
    */ 
    payload = new SomePayload();
    
    /**
    * @type {OtherPayload}
    */ 
    payloadB = new OtherPayload();
    
    /**
    * @type {BiFunction<Object,Object,String>}
    */ 
    handleOnChangeBi;
    
    /**
    * @type {Function<Integer,Integer>}
    */ 
    handleOnChangeD = (b) => 
    {
        return b+1;
    };
    
    /**
    * @type {number}
    */ 
    someInteger;
    
    /**
    * @returns {void}
    */ 
    componentDidMount()
    {
        this.run();
    }
    
    /**
    * @param {Object} a
    * @param {Object} b
    * @returns {String}
    */ 
    some(a,b)
    {
        return "";
    }
    
    /**
    * @param {Object} a
    * @returns {String}
    */ 
    someS(a)
    {
        console.log("SC");
        console.log(a);
        return "";
    }
    
    /**
    * @returns {void}
    */ 
    run()
    {
        this.page=2;
        this.stateKey("name","Some new val");
        this.handleOnChangeD(1);
        this.handleOnChangeBi=this.some;
        this.handleOnChangeBi(new Object(),this.payload);
        
        /**
        * @type {ServiceSome}
        */ 
        let serviceSome = new ServiceSome();
        serviceSome.compute(this.someS,this.someS);
        
        /**
        * @type {Map<String,String>}
        */ 
        let b = new Map();
        WorkerServer.compute({
            onError: function(error)
            {
            },
            onCompletion: function(value)
            {
                console.log("C");
                console.log(value);
                this.stateKey("name",value);
            },
            
            /**
            * @type {String}
            */ 
            endpoint: "EcmaComponentWorker_run_0",
            
            /**
            * @type {Object}
            */ 
            data: {
                
                /**
                * @type {Array<>}
                */ 
                a: this.state.a,
                
                /**
                * @type {SomePayload}
                */ 
                payload: this.payload,
                
                /**
                * @type {Date}
                */ 
                dateTime: this.dateTime,
                
                /**
                * @type {number}
                */ 
                page: this.page,
                
                /**
                * @type {String}
                */ 
                name: this.state.name
            },
            
            /**
            * @type {Object}
            */ 
            bind: this
        });
        WorkerServer.compute({
            onError: function(error)
            {
            },
            onCompletion: function(value)
            {
                this.stateKey("aa",value);
            },
            
            /**
            * @type {String}
            */ 
            endpoint: "EcmaComponentWorker_run_1",
            
            /**
            * @type {Object}
            */ 
            data: {
                
                /**
                * @type {String}
                */ 
                name: this.state.name
            },
            
            /**
            * @type {Object}
            */ 
            bind: this
        });
    }
    
    render()
    {
        return(
            <div>
                <div>Hello component. Requestd name: {this.state.name}</div>
                <EcmaInner propName={this.state.name}/>
                <div>
                    {this.state.aa.map(value => (
                        <span>{value},</span>
                    ))}
                </div>
            </div>
        );
    }
}