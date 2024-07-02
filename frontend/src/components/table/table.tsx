import {
  DataGrid,
  GridSortModel,
  GridSortDirection,
  GridRowsProp,
  GridColDef,
} from '@mui/x-data-grid';

interface DataTableProps {
  rows: GridRowsProp<Record<string, any>>;
  columns: GridColDef<Record<string, any>>[];
  onSortChange: (field: string, direction: GridSortDirection) => void;
}

export const DataTable: React.FC<DataTableProps> = ({ rows, columns, onSortChange }) => {
  const handleSortModelChange = (model: GridSortModel) => {
    if (!model || model.length === 0) {
      return;
    }

    console.log('hello sort!', model);

    const sortItem = model[0];
    onSortChange(sortItem.field, sortItem.sort);
  };

  return (
    <DataGrid
      rows={rows || []}
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
