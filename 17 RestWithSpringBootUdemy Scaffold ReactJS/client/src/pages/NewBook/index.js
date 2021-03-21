import React from 'react';
import {Link} from 'react-router-dom';
import {FiArrowLeft} from 'react-icons/fi';

import './styles.css';
import logoImage from '../../assets/logo.svg';

function NewBook() {
    return (
        <div className="new-book-container">
            <div className="content">
                <section className="form">
                    <img src={logoImage} alt="Almussio" />
                    <h1>Cadastrar novo livro</h1>
                    <p>Informe os dados do livro e clique em Adicionar</p>
                    <Link className="back-link" to="/books">
                        <FiArrowLeft size={16} color="#251FC5" />
                        Página inicial
                    </Link>
                </section>
                <form>
                    <input placeholder="Título" />
                    <input placeholder="Autor" />
                    <input type="date" />
                    <input placeholder="Preço" />

                    <button type="submit" className="button">Adicionar</button>
                </form>
            </div>
        </div>  
    );
}

export default NewBook;