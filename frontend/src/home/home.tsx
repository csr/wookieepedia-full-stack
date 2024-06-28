import DataTable from '../table/table';

const Home = () => {
  return (
    <div>
      <header>
        <h1 className="star-wars-logo">WOOKIEEPEDIA</h1>
        <nav className="nav-bar">
          <ul>
            <li>
              <a href="#">PEOPLE</a>
            </li>
            <li>
              <a href="#">PLANETS</a>
            </li>
          </ul>
        </nav>
      </header>
      <main className="main-content">
        <DataTable />
      </main>
    </div>
  );
};

export default Home;
