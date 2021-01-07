import {WorkerService} from 'ccbus/WorkerService';
import {FileDownload} from 'ccbus/payload/FileDownload';
import {SomePayload} from 'payload/SomePayload';
import {WorkerServer} from 'ccbus';


/**
* @extends {WorkerService<SomePayload>}
*/ 
export class ServiceSome extends WorkerService
{
    
    /**
    * @type {String}
    */ 
    someVal = "Hello";
    
    /**
    * @type {number}
    */ 
    ival = 1;
    
    /**
    * @param {String} someVal
    * @returns {void}
    */ 
    setSomeVal(someVal)
    {
        this.someVal=someVal;
    }
    
    /**
    * @returns {}
    */ 
    compute()
    {
        WorkerServer.computeService({
            
            /**
            * @type {String}
            */ 
            endpoint: "service.ServiceSome",
            
            /**
            * @type {Object}
            */ 
            data: {
                
                /**
                * @type {number}
                */ 
                ival: this.ival,
                
                /**
                * @type {String}
                */ 
                someVal: this.someVal
            },
            
            /**
            * @type {Object}
            */ 
            bind: this,
            
            /**
            * @type {Object}
            */ 
            args: arguments
        })
    }
}