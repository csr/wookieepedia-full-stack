import { DataGrid } from '@mui/x-data-grid';
//import people from 'swapi-people.json';
//import peopleColumns from 'swapi-people-columns.json';

import { usePeopleColumns, usePeopleData } from '@/api';

//import planets from 'swapi-planets.json';
//import planetsColumns from 'swapi-planets-columns.json';

//const peopleRows = people.results;
//const planetsRows = planets.results;

export enum TableDataType {
  People,
  Planets,
}

interface DataTableProps {
  type: TableDataType;
}

export const DataTable: React.FC<DataTableProps> = props => {
  const { data: columns } = usePeopleColumns();
  const { data: people } = usePeopleData();

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
