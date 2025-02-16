import React from "react";

class ErrorBoundary extends React.Component {
    constructor(props) {
        super(props);
        this.state = { hasError: false, error: '', info: '', stack: '' };
    }

    static getDerivedStateFromError(error) {
        return { hasError: true };
    }

    componentDidCatch(error, errorInfo) {
        this.setState( {error, info: errorInfo, stack: error.stack} );
    }

    render() {
        if (this.state.hasError) {
            return (
                <div>
                    <h1>An error occurred: {this.state.error.status} {this.state.error.statusText}</h1>
                    <div>stack: {this.state.info}</div>
                    <div>stack: {this.state.stack}</div>
                </div>
            );
        }

        return this.props.children;
    }
}
export default ErrorBoundary;