import { useState } from 'react';
import { DataGrid, GridSortModel, GridSortDirection } from '@mui/x-data-grid';
import { usePeopleColumns, usePeopleData } from '@/api';

export enum TableDataType {
  People,
  Planets,
}

interface DataTableProps {
  type: TableDataType;
  searchTerm?: string;
}

export const DataTable: React.FC<DataTableProps> = props => {
  const { searchTerm } = props;

  const [sortField, setSortField] = useState<string>('');
  const [sortOrder, setSortOrder] = useState<GridSortDirection>('asc');

  const handleSortModelChange = (model: GridSortModel) => {
    if (!model) {
      return;
    }

    const sortItem = model[0];

    setSortField(sortItem.field);
    setSortOrder(sortItem.sort);
  };

  const { data: columns } = usePeopleColumns();
  const { data: people } = usePeopleData(searchTerm, sortField, sortOrder);

  return (
    <DataGrid
      rows={people || []}
      getRowId={row => row.url}
      columns={columns || []}
      style={{ backgroundColor: '#0E1117' }}
      sortingOrder={['asc', 'desc']}
      onSortModelChange={handleSortModelChange}
      initialState={{
        pagination: {
          paginationModel: { page: 0, pageSize: 15 },
        },
      }}
      pageSizeOptions={[15]}
    />
  );
};
