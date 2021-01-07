import {Error} from 'ccbus';
import {ExtComponent} from 'ccbus';
import {WorkerServer} from 'ccbus';
import {FileSaver} from 'ccbus/lib/FileSaver';
import {FileDownload} from 'ccbus/payload/FileDownload';
import {FileUpload} from 'ccbus/payload/FileUpload';
import React from 'react';


/**
* @extends {ExtComponent}
*/ 
export class Downloader extends ExtComponent
{
    
    /**
    * @param {Event} e
    * @returns {void}
    */ 
    onClick(e)
    {
        this.download();
    }
    
    /**
    * @returns {void}
    */ 
    download()
    {
        WorkerServer.compute({
            onError: function(error)
            {
            },
            onCompletion: function(fileDownload)
            {
                FileSaver.saveAs(fileDownload.fileInputStream,fileDownload.fileName);
            },
            
            /**
            * @type {String}
            */ 
            endpoint: "Downloader_download_0",
            
            /**
            * @type {String}
            */ 
            responseType: "blob",
            
            /**
            * @type {Object}
            */ 
            data: {
                
            },
            
            /**
            * @type {Object}
            */ 
            bind: this
        });
    }

    render()
    {
        return(<span><button onClick={(e)=>{this.onClick(e)}}>download</button></span>)
    }
}