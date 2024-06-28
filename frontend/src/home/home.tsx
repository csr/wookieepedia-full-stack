import { useState } from 'react';
import SearchBar from '../search-bar/search-bar';
import DataTable from '../table/table';
import './home.css';

const Home = () => {
  const [searchTerm, setSearchTerm] = useState('');

  const handleSearchChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setSearchTerm(event.target.value);
  };

  return (
    <div className="container">
      <header>
        <h1 className="star-wars-logo"> WOOKIEEPEDIA!</h1>
        <nav className="nav-bar">
          <ul>
            <li>
              <a href="#">🤖 PEOPLE</a>
            </li>
            <li>
              <a href="#">🪐 PLANETS</a>
            </li>
          </ul>
        </nav>
      </header>
      <main className="main-content">
        <SearchBar
          value={searchTerm}
          onChange={handleSearchChange}
          placeholder={'Search people...'}
        />
        <DataTable />
      </main>
    </div>
  );
};

export default Home;
