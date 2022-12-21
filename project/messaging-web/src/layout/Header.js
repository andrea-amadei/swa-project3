import './Header.scss'
import {Link, useLocation} from "react-router-dom";

function Header({ token }) {
  const isHomepage = useLocation().pathname === '/';
  
  return (
    <div className="header">
      <h2><Link to="/">{isHomepage ? 'Messaging' : '< back'}</Link></h2>
      <div><b>Session token:</b> {token}</div>
    </div>
  );
}

export default Header;