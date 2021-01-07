import React from 'react';
import logo from './logo.svg';
import './App.css';
import {WorkerServer} from "ccbus";
import config from "ccbus-config";
import {EcmaComponentWorker} from "components/EcmaComponentWorker";
import {ServiceSome} from "./service/ServiceSome";
import {Uploader} from "components/Uploader";
import {Downloader} from "components/Downloader";

function App() {
    WorkerServer.setConfig(config);
    let service=new ServiceSome((value)=>{console.log("S");console.log(value)},
        (error)=>{console.log(error)});
    service.compute(
            // (value)=>{console.log(value)},
            // (error)=>{console.log(error)}
    )
  return (
    <div className="App">
      <header className="App-header">
          <EcmaComponentWorker />
        <img src={logo} className="App-logo" alt="logo" />
        <p>
          Edit <code>src/App.js</code> and save to reload.
        </p>
        <a
          className="App-link"
          href="https://reactjs.org"
          target="_blank"
          rel="noopener noreferrer"
        >
          Learn React
        </a>
          <Uploader/>
          <Downloader/>
      </header>

    </div>
  );
}

export default App;
