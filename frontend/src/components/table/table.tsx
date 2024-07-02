import { DataGrid } from '@mui/x-data-grid';
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
  const { data: columns } = usePeopleColumns();
  const { data: people } = usePeopleData(searchTerm);

  return (
    <DataGrid
      rows={people || []}
      getRowId={row => row.url}
      columns={columns || []}
      style={{ backgroundColor: '#0E1117' }}
      initialState={{
        pagination: {
          paginationModel: { page: 0, pageSize: 15 },
        },
      }}
      pageSizeOptions={[15]}
    />
  );
};
