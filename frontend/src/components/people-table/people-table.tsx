import React, { useState } from 'react';
import { DataTable } from '@/components/table';
import { usePeopleColumns, usePeopleData, usePlanetsColumns } from '@/api';
import { GridSortDirection } from '@mui/x-data-grid';

export const PeopleTable: React.FC<{ searchTerm?: string }> = ({ searchTerm }) => {
  const [sortField, setSortField] = useState<string>('');
  const [sortOrder, setSortOrder] = useState<GridSortDirection>('asc');

  const { data: columns } = usePeopleColumns();
  const { data: people } = usePeopleData(searchTerm, { sortBy: sortField, sortOrder });

  const handleSortChange = (field: string, direction: GridSortDirection) => {
    setSortField(field);
    setSortOrder(direction);
  };

  return <DataTable rows={people || []} columns={columns || []} onSortChange={handleSortChange} />;
};
