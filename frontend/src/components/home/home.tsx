import { useState } from 'react';
import Box from '@mui/material/Box';
import Tabs from '@mui/material/Tabs';
import Tab from '@mui/material/Tab';
import { SearchBar } from '@/components/search-bar';
import { PeopleTable } from '@/components/people-table';
import { PlanetsTable } from '@/components/planets-table';

import './home.css';

const Home = () => {
  const [currentTabId, setCurrentTabId] = useState(0);

  const [peopleSearchTerm, setPeopleSearchTerm] = useState<string>('');
  const [planetsSearchTerm, setPlanetsSearchTerm] = useState<string>('');

  const handlePeopleSearchChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setPeopleSearchTerm(event.target.value);
  };

  const handlePlanetsSearchChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setPlanetsSearchTerm(event.target.value);
  };

  const handleTabChange = (event: React.SyntheticEvent, newValue: number) => {
    setCurrentTabId(newValue);
  };

  return (
    <div className="container">
      <header>
        <h1 className="star-wars-logo"> WOOKIEEPEDIA!</h1>
        <Box>
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
              value={peopleSearchTerm}
              onChange={handlePeopleSearchChange}
              placeholder="Search people..."
            />
            <PeopleTable searchTerm={peopleSearchTerm} />
          </>
        )}
        {currentTabId === 1 && (
          <>
            <SearchBar
              value={planetsSearchTerm}
              onChange={handlePlanetsSearchChange}
              placeholder="Search planets..."
            />
            <PlanetsTable searchTerm={planetsSearchTerm} />
          </>
        )}
      </main>
    </div>
  );
};

export default Home;
