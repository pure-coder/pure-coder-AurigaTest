import React, { Component } from 'react';
import '../../App.css';

class Landing_page extends Component {
    state = {
        isLoading: true,
        clientDetails: null
    };

    // Get the requested data for device details from Java REST API
    async componentDidMount() {
        const response = await fetch('/client_response');
        const jsonBody = await response.json();
        this.setState({ clientDetails: jsonBody, isLoading: false });
    }

    render() {
        const {clientDetails, isLoading} = this.state;

        // Show loading message whilst waiting for data
        if (isLoading) {
            return <p>Loading...</p>;
        }

        // When data has been returned display data in table.
        return (
            <div className="Landing_page-container">
                <div className="Landing_page-body">
                    <h2>Client Device Details</h2>
                    <table>
                        <tr>
                            Name:
                            <td>{clientDetails.name}</td>
                        </tr>
                        <tr>
                            Agent Version:
                            <td>{clientDetails.agentVersion}</td>
                        </tr>
                        <tr>
                            How Many Alerts:
                            <td>{clientDetails.howManyAlerts}</td>
                        </tr>
                        <tr>
                            Architecture:
                            <td>{clientDetails.architecture}</td>
                        </tr>
                        <tr>
                            Collector:
                            <td>{clientDetails.collector}</td>
                        </tr>
                        <tr>
                            CPU Model:
                            <td>{clientDetails.cpuModel}</td>
                        </tr>
                        <tr>
                            Description:
                            <td>{clientDetails.description}</td>
                        </tr>
                        <tr>
                            Discovery Date:
                            <td>{clientDetails.discoveryDate}</td>
                        </tr>
                        <tr>
                            IP Addresses:
                            <td>{clientDetails.ipAddresses[0]}, {clientDetails.ipAddresses[1]}</td>
                        </tr>
                    </table>
                </div>
            </div>
        );
    }
}

export default Landing_page;