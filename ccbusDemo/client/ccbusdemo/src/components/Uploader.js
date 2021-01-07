import {ExtComponent} from 'ccbus';


import React from 'react';

import {Error} from 'ccbus';
import {WorkerServer} from 'ccbus';


import {FileUpload} from 'ccbus/payload/FileUpload';


/**
* @extends {ExtComponent}
*/ 
export class Uploader extends ExtComponent
{
    
    /**
    * @type {FileUpload}
    */ 
    fileUpload = new FileUpload();
    
    /**
    * @type {number}
    */ 
    val = 1;
    
    /**
    * @param {Event} e
    * @returns {void}
    */ 
    onChange(e)
    {
        this.fileUpload.addWebFiles(e.target.files);
        if(this.fileUpload.files.length>0)
        {
            this.uploadFiles();
        }
    }
    
    /**
    * @returns {void}
    */ 
    uploadFiles()
    {
        WorkerServer.compute({
            onError: function(error)
            {
            },
            onCompletion: function(aBoolean)
            {
                this.fileUpload.clear();
            },
            
            /**
            * @type {String}
            */ 
            endpoint: "Uploader_uploadFiles_0",
            
            /**
            * @type {Object}
            */ 
            data: {
                
                /**
                * @type {number}
                */ 
                val: this.val,
                
                /**
                * @type {FileUpload}
                */ 
                fileUpload: this.fileUpload
            },
            
            /**
            * @type {Object}
            */ 
            bind: this
        });
    }
    
    render()
    {
        return(<div> <input type="file" class="form-control" multiple  onChange={(e)=>{this.onChange(e)}}/></div>)
    }
}