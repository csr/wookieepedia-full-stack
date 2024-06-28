// SearchBar.js
import React from 'react';
import { TextField, InputAdornment } from '@mui/material';
import SearchIcon from '@mui/icons-material/Search';

interface SearchBarProps {
  value: string;
  onChange: (event: React.ChangeEvent<HTMLInputElement>) => void;
  placeholder?: string;
}

const SearchBar = (props: SearchBarProps) => {
  const { value, onChange, placeholder } = props;

  return (
    <TextField
      value={value}
      onChange={onChange}
      variant="outlined"
      placeholder={placeholder || 'Search...'}
      InputProps={{
        startAdornment: (
          <InputAdornment position="start">
            <SearchIcon />
          </InputAdornment>
        ),
      }}
      fullWidth
    />
  );
};

export default SearchBar;
