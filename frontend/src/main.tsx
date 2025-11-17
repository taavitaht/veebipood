import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import 'bootstrap/dist/css/bootstrap.min.css'
import './index.css'
import './i18n';
import App from './App.tsx'
import { BrowserRouter } from 'react-router-dom'

// Navigeerimiseks (URL vahetamiseks)
// 1. npm i react-router-dom (vajalikud koodifailid panema node_modules kausta)
// 2. import {BrowserRouter} (importima äsjalisatud failidest BrowserRouteri)
// 3. <BrowserRouter><App/></> (ümbritsema App faili BrowserRouteriga)
// 4. tegema App.jsx failis URLi ja faili vahelisi seoseid

createRoot(document.getElementById('root')!).render(
  <StrictMode>
    <BrowserRouter>
      <App />
    </BrowserRouter>
  </StrictMode>,
)
