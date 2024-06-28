import DataTable from '../table/table';
import './home.css';

const Home = () => {
  return (
    <div className="container">
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
