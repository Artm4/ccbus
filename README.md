# ccbus
* Tool for translation from Java code (Components and Services) to Babel javascript (extension of Ecmascript 6.0) and Java desktop client service for cross-platform development.
* Java API and React API to unify server - client communication.
* Db API wrapper for JPA.
* Db meta tool. Creates entity meta graph for typed queries.

**Check ccbusDoc for instructions.**

###Examples:

**Server upload component**
```
import ccbus.connect.core.ccbus.Error;
import ccbus.connect.core.ccbus.ExtComponent;
import ccbus.connect.core.ccbus.WorkerServer;
import ccbus.connect.core.ccbus.payload.FileUpload;
import ccbus.connect.ecma.Event;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Uploader extends ExtComponent {
    FileUpload fileUpload=new FileUpload();
    int val=1;

    void onChange(Event e)
    {
        fileUpload.addWebFiles(e.target.files);
        if(fileUpload.files.size()>2) {
            uploadFiles();
        }
    }

    void uploadFiles()
    {
        new WorkerServer<Boolean>()
        {
            @Override
            public void compute() {
                int myVal=val;
                try {
                    for(int i=0;i<fileUpload.files.size();i++)
                    {
                        String name=fileUpload.files.get(i).origName;
                        Files.copy(fileUpload.files.get(i).getPart().getInputStream(),
                                Paths.get(this.getClass().getClassLoader().getResource("public/pics").getPath() + "/" + name));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                this.complete(true);
            }

            @Override
            public void onCompletion(Boolean aBoolean) {
                fileUpload.clear();
            }

            @Override
            public void onError(Error error) {

            }
        };
    }
}
```

**Translated unit for React web client**
```
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
        return(<span>!</span>)
    }
}
```
_Render implementation has to be implemented from client side, but it is kept untouched by the translator_
```
render()
{
    return(<div> <input type="file" class="form-control" multiple  onChange={(e)=>{this.onChange(e)}}/></div>)
}
```



**Server service**
```
package ccbus.demo.ccbus.worker.service;

import ccbus.connect.core.ccbus.WorkerServerImpl;

import ccbus.connect.core.ccbus.WorkerService;
import ccbus.connect.core.ccbus.payload.FileDownload;
import ccbus.connect.core.ccbus.payload.FileUpload;
import ccbus.demo.ccbus.payload.SomePayload;

import java.io.File;

public class ServiceDownload extends WorkerServerImpl<FileDownload>
{
    public  void compute()
    {

        String fileName="Screenshot_20200108.png";
        File file = new File(this.getClass().getClassLoader().getResource("public/pics/").getPath()+fileName);
        this.complete(new FileDownload(file));
    }
    
}
```

**Translated service unit for React web client**
```
package ccbus.demo.ccbus.worker.service;

import ccbus.connect.core.ccbus.WorkerServerImpl;

import ccbus.connect.core.ccbus.WorkerService;
import ccbus.connect.core.ccbus.payload.FileDownload;
import ccbus.connect.core.ccbus.payload.FileUpload;
import ccbus.demo.ccbus.payload.SomePayload;

import java.io.File;

public class ServiceDownload extends WorkerServerImpl<FileDownload>
{
    public  void compute()
    {

        String fileName="Screenshot_20200108.png";
        File file = new File(this.getClass().getClassLoader().getResource("public/pics/").getPath()+fileName);
        this.complete(new FileDownload(file));
    }
    
}
```

**Server Service #2**
```
package com.art.tms.ccbus.service;

import ccbus.connect.core.ccbus.WorkerService;
import ccbus.connect.core.ccbus.payload.FileDownload;

import java.io.File;


public class ServiceDownload extends WorkerService<FileDownload>
{
    int abs;
    @Override
    public void compute()
    {
        abs=1;
        String fileName="tets.xlsx";
        File file = new File(this.getClass().getClassLoader().getResource("").getPath()+fileName);
        complete(new FileDownload(file));
    }
}
```

**Translated client unit for Java desktop client**

```
package com.art.tms.service;

import ccbus.connect.core.ccbus.payload.FileDownload;
import ccbus.desktop.worker.WorkerCallback;
import ccbus.desktop.worker.WorkerService;

public class ServiceDownload extends WorkerService<FileDownload>
{
    public 
    int abs;
    public void compute(WorkerCallback<FileDownload> callback)
    {
        ServiceDownload ref=this;
        this.setPayload(
            new Object()
            {
                public int abs = ref.abs;
            }
            ,FileDownload.class
            ,"service.ServiceDownload"
            ,"blob"
        );
        super.compute(callback);
    }

    
}
```