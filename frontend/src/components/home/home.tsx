import { useState } from 'react';
import Box from '@mui/material/Box';
import Tabs from '@mui/material/Tabs';
import Tab from '@mui/material/Tab';
import { DataTable } from '@/components/table';
import { SearchBar } from '@/components/search-bar';
import { TableDataType } from '@/components/table';

import './home.css';

const Home = () => {
  const [searchTerm, setSearchTerm] = useState<string>('');

  const [currentTabId, setCurrentTabId] = useState(0);

  const handleSearchChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setSearchTerm(event.target.value);
  };

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
      <main>
        {currentTabId === 0 && (
          <>
            <SearchBar
              value={searchTerm}
              onChange={handleSearchChange}
              placeholder="Search people..."
            />
            <DataTable type={TableDataType.People} searchTerm={searchTerm} />
          </>
        )}
        {currentTabId === 1 && (
          <>
            <SearchBar
              value={searchTerm}
              onChange={handleSearchChange}
              placeholder="Search planets..."
            />
            <DataTable type={TableDataType.Planets} searchTerm={searchTerm} />
          </>
        )}
      </main>
    </div>
  );
};

export default Home;
