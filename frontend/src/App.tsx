import React, { useState } from 'react';
import './App.css';

function App() {
  return (
    <div>
      <header>
        <h1 className="star-wars-logo">WOOKIEEPEDIA</h1>
        <nav className="nav-bar">
          <ul>
            <li><a href="#">PEOPLE</a></li>
            <li><a href="#">PLANETS</a></li>
          </ul>
        </nav>
      </header>
    </div>
  );
}

export default App;
