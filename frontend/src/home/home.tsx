import { useState } from 'react';
import Box from '@mui/material/Box';
import Tabs from '@mui/material/Tabs';
import Tab from '@mui/material/Tab';
import SearchBar from '../search-bar/search-bar';
import DataTable from '../table/table';
import './home.css';

const Home = () => {
  const [searchTerm, setSearchTerm] = useState('');

  const handleSearchChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setSearchTerm(event.target.value);
  };

  const [currentTabId, setCurrentTabId] = useState(0);

  const handleTabChange = (event: React.SyntheticEvent, newValue: number) => {
    setCurrentTabId(newValue);
  };

  return (
    <div className="container">
      <header>
        <h1 className="star-wars-logo"> WOOKIEEPEDIA!</h1>
        <Box sx={{ width: '100%' }}>
          <Tabs value={currentTabId} onChange={handleTabChange} centered>
            <Tab label="🤖 PEOPLE" />
            <Tab label="🪐 PLANETS" />
          </Tabs>
        </Box>
      </header>
      <main className="main-content">
        {currentTabId === 0 && (
          <>
            <SearchBar
              value={searchTerm}
              onChange={handleSearchChange}
              placeholder="Search people..."
            />
            <DataTable /> {/* People Data Table */}
          </>
        )}
        {currentTabId === 1 && (
          <>
            <SearchBar
              value={searchTerm}
              onChange={handleSearchChange}
              placeholder="Search planets..."
            />
            <div>Planets will go here.</div>
          </>
        )}
      </main>
    </div>
  );
};

export default Home;
