import { useState, useEffect } from 'react';
import Box from '@mui/material/Box';
import Tabs from '@mui/material/Tabs';
import Tab from '@mui/material/Tab';
import { DataTable } from '@/components/table';
import { SearchBar } from '@/components/search-bar';
import './home.css';
import { TableDataType } from '@/components/table';

const Home = () => {
  const [searchTerm, setSearchTerm] = useState<string>('');
  const [debouncedSearchTerm, setDebouncedSearchTerm] = useState<string>('');

  const [currentTabId, setCurrentTabId] = useState(0);

  const handleSearchChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setSearchTerm(event.target.value);
  };

  const handleTabChange = (event: React.SyntheticEvent, newValue: number) => {
    setCurrentTabId(newValue);
  };

  // We're using debouncing here! It's basically waiting until the user stops typing to send less requests
  // and make the user interface feel more responsive
  useEffect(() => {
    const handler = setTimeout(() => {
      setDebouncedSearchTerm(searchTerm);
    }, 150);

    return () => {
      clearTimeout(handler);
    };
  }, [searchTerm]);

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
            <DataTable type={TableDataType.People} searchTerm={debouncedSearchTerm} />
          </>
        )}
        {currentTabId === 1 && (
          <>
            <SearchBar
              value={searchTerm}
              onChange={handleSearchChange}
              placeholder="Search planets..."
            />
            <DataTable type={TableDataType.Planets} searchTerm={debouncedSearchTerm} />
          </>
        )}
      </main>
    </div>
  );
};

export default Home;
