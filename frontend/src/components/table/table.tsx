import { DataGrid, GridColDef, GridRow } from '@mui/x-data-grid';
import people from 'swapi-people.json';
import peopleColumns from 'swapi-people-columns.json';

import planets from 'swapi-planets.json';
import planetsColumns from 'swapi-planets-columns.json';

const peopleRows = people.results;
const planetsRows = planets.results;

export enum TableDataType {
  People,
  Planets,
}

interface DataTableProps {
  type: TableDataType;
}

export const DataTable: React.FC<DataTableProps> = props => {
  const { type } = props;

  let rows: Record<string, any>[] = [];
  let columns: GridColDef[] = [];

  if (type === TableDataType.People) {
    rows = peopleRows;
    columns = peopleColumns as GridColDef[];
  } else if (type === TableDataType.Planets) {
    rows = planetsRows;
    columns = planetsColumns as GridColDef[];
  }

  return (
    <DataGrid
      rows={rows}
      getRowId={row => row.url}
      columns={columns}
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
