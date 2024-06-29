import React from 'react';
import { TextField, InputAdornment } from '@mui/material';
import SearchIcon from '@mui/icons-material/Search';

interface SearchBarProps {
  value: string;
  onChange: (event: React.ChangeEvent<HTMLInputElement>) => void;
  placeholder?: string;
}

export const SearchBar: React.FC<SearchBarProps> = props => {
  const { value, onChange, placeholder } = props;

  return (
    <TextField
      value={value}
      onChange={onChange}
      variant="outlined"
      placeholder={placeholder || 'Search...'}
      style={{ backgroundColor: '#161b22' }}
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
