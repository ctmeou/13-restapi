import Header from "../components/common/Header";
import Navbar from "../components/common/Navbar";
import {Outlet} from "react-router-dom";
import Footer from "../components/common/Footer";

function Layout() {

    return (
        <>
            <Header/>
            <Navbar/>
            <main className="main">
                <Outlet/>
            </main>
            <Footer/>
        </>
    );

}

export default Layout;