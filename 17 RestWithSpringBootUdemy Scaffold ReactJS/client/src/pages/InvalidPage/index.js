import React from 'react';
import {Link} from 'react-router-dom';

function InvalidPage() {
    return (
        <div>
            <h1>Página não existente!</h1>
            <p>Clique <Link to="/">aqui</Link> para voltar à página de login...</p>
        </div>
    );
}

export default InvalidPage;