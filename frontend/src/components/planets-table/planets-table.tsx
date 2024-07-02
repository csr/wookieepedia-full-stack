import React, { useState } from 'react';
import { DataTable } from '@/components/table';
import { GridSortDirection } from '@mui/x-data-grid';
import { usePlanetsColumns, usePlanetsData } from '@/api';

export const PlanetsTable: React.FC<{ searchTerm?: string }> = ({ searchTerm }) => {
  const [sortField, setSortField] = useState<string>('');
  const [sortOrder, setSortOrder] = useState<GridSortDirection>('asc');

  const { data: columns } = usePlanetsColumns();
  const { data: planets } = usePlanetsData(searchTerm, { sortBy: sortField, sortOrder });

  const handleSortChange = (field: string, direction: GridSortDirection) => {
    setSortField(field);
    setSortOrder(direction);
  };

  return <DataTable rows={planets || []} columns={columns || []} onSortChange={handleSortChange} />;
};
