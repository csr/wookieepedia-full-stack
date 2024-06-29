import { DataGrid, GridColDef } from '@mui/x-data-grid';
import people from 'swapi-people.json';

const columns: GridColDef[] = [
  {
    field: 'name',
    headerName: 'Name',
    description: 'The name of the character.',
    sortable: true,
    width: 150,
  },
  {
    field: 'height',
    headerName: 'Height',
    description: 'The height of the character in centimeters.',
    sortable: false,
    width: 150,
  },
  {
    field: 'mass',
    headerName: 'Mass',
    description: 'The mass of the character in kilograms.',
    sortable: false,
    width: 150,
  },
  {
    field: 'hair_color',
    headerName: 'Hair Color',
    description: "The color of the character's hair.",
    sortable: false,
    width: 150,
  },
  {
    field: 'skin_color',
    headerName: 'Skin Color',
    description: "The color of the character's skin.",
    sortable: false,
    width: 150,
  },
  {
    field: 'eye_color',
    headerName: 'Eye Color',
    description: "The color of the character's eyes.",
    sortable: false,
    width: 150,
  },
  {
    field: 'birth_year',
    headerName: 'Birth Year',
    description: 'The birth year of the character.',
    sortable: false,
    width: 150,
  },
  {
    field: 'gender',
    headerName: 'Gender',
    description: 'The gender of the character.',
    sortable: false,
    width: 150,
  },
  {
    field: 'homeworld',
    headerName: 'Homeworld',
    description: "The URL of the character's homeworld.",
    sortable: false, // Assuming it's not directly sortable
    width: 150,
  },
  {
    field: 'films',
    headerName: 'Films',
    description: 'The URLs of the films the character appears in.',
    sortable: false, // Assuming it's not directly sortable
    width: 150,
  },
  {
    field: 'vehicles',
    headerName: 'Vehicles',
    description: 'The URLs of the vehicles the character owns.',
    sortable: false, // Assuming it's not directly sortable
    width: 150,
  },
  {
    field: 'starships',
    headerName: 'Starships',
    description: 'The URLs of the starships the character owns.',
    sortable: false, // Assuming it's not directly sortable
    width: 150,
  },
  {
    field: 'created',
    headerName: 'Created',
    description: 'The date and time the character entry was created.',
    sortable: true,
    width: 150,
  },
  {
    field: 'edited',
    headerName: 'Edited',
    description: 'The date and time the character entry was last edited.',
    sortable: false,
    width: 150,
  },
  {
    field: 'url',
    headerName: 'URL',
    description: 'The URL of the character resource.',
    sortable: false, // Assuming it's not directly sortable
    width: 150,
  },
];

const rows = people.results;

export const DataTable = () => {
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
