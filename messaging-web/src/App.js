import './App.scss'
import Header from "./layout/Header";
import {BrowserRouter, Outlet, Route, Routes} from "react-router-dom";
import HomePage from "./layout/HomePage";
import {useState} from "react";
import ElementPage from "./layout/ElementPage";

function App() {
  
  const [selectedThread, setSelectedThread] = useState(undefined);
  
  return (
    <div className="app">
      <BrowserRouter>
  
        <Header token={localStorage.getItem("session_token")} />
        
        <Routes>
          <Route path="/:id" element={<ElementPage selectedElement={selectedThread} />} />
          <Route path="/" index element={<HomePage setSelectedThread={setSelectedThread} />} />
        </Routes>
        
        <Outlet />

      </BrowserRouter>
    </div>
  );
}

export default App;
