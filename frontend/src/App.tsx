import './App.css';
import { ThemeProvider, createTheme } from '@mui/material/styles';
import CssBaseline from '@mui/material/CssBaseline';
import { QueryClient, QueryClientProvider } from 'react-query';
import config from './config';

import Home from './components/home/home';
import { useEffect } from 'react';

const darkTheme = createTheme({
  palette: {
    mode: 'dark',
    primary: {
      main: '#ffd700', // gold accent, very Star Wars-y :)
    },
  },
});

const queryClient = new QueryClient();

function App() {
  useEffect(() => {
    console.log('REACT_APP_BACKEND_APP_API_BASE_URL:', config.backendAppApiUrl);
    console.log('REACT_APP_BACKEND_APP_API_PREFIX:', config.backendAppApiPrefix);
  }, []);

  return (
    <QueryClientProvider client={queryClient}>
      <ThemeProvider theme={darkTheme}>
        <CssBaseline />
        <Home />
      </ThemeProvider>
    </QueryClientProvider>
  );
}

export default App;
