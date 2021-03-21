import React from 'react';
import {BrowserRouter, Route, Switch} from 'react-router-dom';
import Login from './pages/Login';
import Books from './pages/Books';
import NewBook from './pages/NewBook';
import InvalidPage from './pages/InvalidPage';

function Routes() {
    return (
        <BrowserRouter>
            <Switch>
                <Route path="/" exact component={Login} />
                <Route path="/books" component={Books} />
                <Route path="/book/new" component={NewBook} />

                <Route path="*" component={InvalidPage} />
            </Switch>
        </BrowserRouter>
    );
}

export default Routes;